package it.polimi.demo.controller;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.*;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.networking.ControllerInterfaces.GameControllerInterface;
import it.polimi.demo.view.GameDetails;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class GameController implements GameControllerInterface, Runnable, Serializable {

    /**
     * The model to control
     */
    private GameModel model;

    /**
     * A random object for implementing pseudo-random choice
     */
    private final Random random = new Random();

    /**
     * This map associate a GameListener to a Ping and the nick of the player.
     */
    private final Map<GameListener, Heartbeat> listeners_to_heartbeats;

    /**
     * Timer started when only one player is playing
     * it ends the game if no one reconnects within seconds
     */
    private Thread reconnection_thread;

    public GameController() {
        model = new GameModel();
        listeners_to_heartbeats = new HashMap<>();
        new Thread(this).start();
    }

    public GameController(GameModel game_model) {
        model = game_model;
        listeners_to_heartbeats = new HashMap<>();
        new Thread(this).start();
    }

    public GameModel getModel() {
        return model;
    }

    //------------------------------------gameFlow-----------------------------------------------
    public void gameFlow() throws RemoteException, GameEndedException {
        switch(model.getStatus()){
            case WAIT:
                //TODO: aspettare tutti i giocatori previsti per la partita
                if(model.getNumPlayersToPlay() == getNumConnectedPlayers())
                    model.setStatus(GameStatus.FIRST_ROUND);
            case FIRST_ROUND:
                //TODO giocare il primo turno posizionando le carte starter
                if(model.getPlayersConnected().getFirst().getNickname().equals(model.getBeginnerPlayer().getNickname()))
                    model.setStatus(GameStatus.RUNNING);
            case RUNNING:
                //TODO giocare
                if(model.getPlayersConnected().getFirst().getCurrentPoints() >= 20) {
                    model.nextTurn();
                    model.setStatus(GameStatus.SECOND_LAST_ROUND);
                }
            case SECOND_LAST_ROUND:
                //TODO giocare normale
                if(model.getPlayersConnected().getFirst().getNickname().equals(model.getBeginnerPlayer().getNickname())) {
                    model.nextTurn();
                    model.setStatus(GameStatus.LAST_ROUND);
                }
            case LAST_ROUND:
                //TODO giocare senza pescare
                if(model.getPlayersConnected().getFirst().getNickname().equals(model.getBeginnerPlayer().getNickname()))
                    model.setStatus(GameStatus.ENDED);
            case ENDED:
                //TODO giocare senza pescare
                model.calculateFinalScores();
        }

    }


    //------------------------------------connection/reconnection management-----------------------------------------------
    // Threads management ---------------------------------------
    /**
     * This method is called when the thread is started.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (model.getStatus().equals(GameStatus.RUNNING) ||
                    model.getStatus().equals(GameStatus.LAST_ROUND) ||
                    model.getStatus().equals(GameStatus.ENDED) ||
                    model.getStatus().equals(GameStatus.SECOND_LAST_ROUND) ||
                    model.getStatus().equals(GameStatus.WAIT)) {
                checkForDisconnections();
                pauseThread();
            }
        }
    }

    /**
     * It pauses the thread for 500 milliseconds
     */
    private void pauseThread() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    // -------------------------------------------------------

    /**
     * Check if a player is disconnected by checking the heartbeat freshness, if it is expired, the player is disconnected
     * and handleDisconecction will deal with it.
     */
    private void checkForDisconnections() {
        synchronized (listeners_to_heartbeats) {
            for (Map.Entry<GameListener, Heartbeat> entry : listeners_to_heartbeats.entrySet()) {
                GameListener listener = entry.getKey();
                Heartbeat heartbeat = entry.getValue();

                if (isHeartbeatExpired(heartbeat)) {
                    handleDisconnection(heartbeat, listener);
                }
            }
        }
    }

    /**
     * It checks if the heartbeat is expired.
     * It returns true if the difference between the current time and the last ping time is greater than the timeout.
     * @param heartbeat
     * @return
     */
    private boolean isHeartbeatExpired(Heartbeat heartbeat) {
        long currentTime = System.currentTimeMillis();
        long lastBeatTime = heartbeat.getPing();
        // Timeout =
        return currentTime - lastBeatTime > DefaultValues.timeout;
    }

    /**
     * It handles the disconnection of a player.
     * This is the key method for the disconnection management, it calss the disconnectPlayer method and
     * removes the listener from the list of listeners_to_heartbeats.
     * @param heartbeat
     * @param listener
     */
   private void handleDisconnection(Heartbeat heartbeat, GameListener listener) {
    try {
        disconnectPlayer(heartbeat.getPlayer(), listener);
        printAsync("Disconnection of player: " + heartbeat.getPlayer().getNickname() + " detected ");

        // Now check
        if (getNumConnectedPlayers() == 0) {
            stopTimer();
            MainController.getControllerInstance().deleteGame(getGameId());
        }
    } catch (RemoteException | GameEndedException e) {
        throw new RuntimeException(e);
    }

    listeners_to_heartbeats.remove(listener);
    }

    /**
     * Add a Ping to the list of listeners_to_heartbeats.
     *
     * Remark: this method is quite fundamental for the right behavior of the game, since we expect
     * to call it frequently to check if the players are still connected. In fact, looking at the run method,
     * it is clear that if the heartbeat related to a certain player is not 'fresh' in some sense, then indeed
     * the player will appear to us as disconnected.
     *
     * @param nickname the player's nickname associated to the heartbeat
     * @param me   the player's GameListener associated to the heartbeat
     * @throws RemoteException
     */
    @Override
    public synchronized void addPing(String nickname, GameListener me) throws RemoteException {
        synchronized (listeners_to_heartbeats) {
            listeners_to_heartbeats.put(me, new Heartbeat(System.currentTimeMillis(), getPlayerEntity(nickname)));
        }
    }

    /**
     * Disconnect the player, if the game is in status, the player is removed from the game
     * If only one player is connected, a timer of  will be started
     *
     * @param p        the player
     * @param listOfClient the listener of the client
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public void disconnectPlayer(Player p, GameListener listOfClient) throws RemoteException, GameEndedException {

        if (p != null && model.getPlayersConnected().contains(p)) {

            removeListener(listOfClient, p);

            if (model.getStatus().equals(GameStatus.WAIT)){
                    // The game is in Wait (game not started yet), the player disconnected, so I remove him from the game)
                    model.removePlayer(p);
            } else if (model.getStatus().equals(GameStatus.ENDED)) {
                    throw new GameEndedException();
            } else {
                    // The game is running, so I set him as disconnected (He can reconnect soon)
                    model.setPlayerAsDisconnected(p);
            }

            // Check if there is only one player playing
            // If getPlayersConnected().size() == 1 and getPlayersConnected().contains(p), he is the only player! so the game end after a timer.

            if ((model.getStatus().equals(GameStatus.RUNNING) ||
                    model.getStatus().equals(GameStatus.SECOND_LAST_ROUND) ||
                    model.getStatus().equals(GameStatus.LAST_ROUND)) &&
                    model.getPlayersConnected().size() == 1) {
                // Starting a th for waiting until reconnection at least of 1 client to keep playing
                if (reconnection_thread == null) {
                    startReconnectionTimer();
                    printAsync("Starting timer for reconnection waiting: "
                            + DefaultValues.secondsToWaitReconnection + " seconds");
                }
            }
        }
    }

    /**
     * Starts a timer for detecting the reconnection of a player, if no one reconnects in time, the game is over
     */
    @SuppressWarnings("BusyWait")
    private void startReconnectionTimer() {
        reconnection_thread = new Thread(
                () -> {
                    long starting_timer = System.currentTimeMillis();

                    while (reconnection_thread != null &&
                            !reconnection_thread.isInterrupted() &&
                            System.currentTimeMillis() - starting_timer < DefaultValues.secondsToWaitReconnection * 1000) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            //Someone called interrupt on this th (no need to keep waiting)
                        }
                    }
                    printAsync("Timer for reconnection ended");

                    if (model.getPlayersConnected().isEmpty()) {
                        // No players online, I delete the games
                        MainController.getControllerInstance().deleteGame(model.getGameId());
                    } else if (model.getPlayersConnected().size() == 1) {
                        printAsync("\tNo player reconnected on time, just one person is playing, set game to ended!");
                        model.setStatus(GameStatus.ENDED);
                    } else {
                        printAsync("\tA player reconnected on time");
                        this.reconnection_thread = null;
                    }
                }

        );
        reconnection_thread.start();
    }

    /**
     * It stops the timer (if started) that checks for reconnections of players
     */
    private void stopTimer() {
        if (reconnection_thread != null) {
            reconnection_thread.interrupt();
            reconnection_thread = null;
        }
        //else It means that a player reconnected but the timer was not started (ex 3 players and 1 disconnects)
    }

    /**
     * Reconnect the player with the nickname @param to the game
     *
     * @throws PlayerAlreadyConnectedException if a player tries to rejoin the same game
     * @throws MaxPlayersLimitException    if there are already 4 players in game
     * @throws GameEndedException       the game is ended
     */
    public int reconnectPlayer(String username){
        int playerIndex = this.model.getAux_order_players().indexOf(username);
        this.model.setPlayerAsConnected(model.getAux_order_players().get(playerIndex));

        if(!this.model.getAux_order_players().get(this.model.getCurrentPlayerIndex()).getIsConnected()){
            this.nextPlayer();
        }

        return playerIndex;
    }

    public void nextPlayer(){
        if(this.model.getPlayersConnected().size() > 1){
            int i = (this.model.getCurrentPlayerIndex() + 1) % this.model.getNumPlayersToPlay();
            while(!this.model.getAux_order_players().get(i).getIsConnected()){
                i = (i + 1) % this.model.getNumPlayersToPlay();
            }
            this.model.setCurrentPlayerIndex(i);
        } else {
            this.model.setAsPaused();
        }
    }

    /**
     * It removes a player by nickname @param nick from the game including the associated listeners
     *
     * @param lis  GameListener to remove
     * @param nick of the player to remove
     * @throws RemoteException
     */
    @Override
    public synchronized void leave(GameListener lis, String nick) throws RemoteException {
        removeListener(lis, model.getPlayerEntity(nick));
        model.removePlayer(model.getPlayerEntity(nick));
    }

    @Override
    public void startIfFull() {
        if(this.getConnectedPlayers().size() == getNumPlayersToPlay()){
            this.startGame();
        }
    }

    @Override
    public void setError(String error){
        this.model.setErrorMessage(error);
    }


    //------------------------------------ management of players -----------------------------------------------
    /**
     * Add player @param p to the Game
     *
     * @throws PlayerAlreadyConnectedException when in the game there is already another Player with the same nickname
     * @throws MaxPlayersLimitException    when the game has already reached its full capability
     */
    public void addPlayer(String nickname) throws PlayerAlreadyConnectedException, MaxPlayersLimitException {
        this.model.addPlayer(nickname);
    }

    /**
     * @return the list of the players currently playing (online and offline)
     */
    public List<Player> getPlayers() {
        return model.getAllPlayers();
    }

    /**
     * @return the list of the players currently connected
     */
    public LinkedList<Player> getConnectedPlayers() {
        return model.getPlayersConnected();
    }

    /**
     * Returns num of current players that are in the game (online and offline)
     *
     * @return num of current players
     */
    public int getNumPlayers() {
        return model.getAllPlayers().size();
    }

    /**
     * Return the entity of the player associated with the nickname @param
     *
     * @param nick
     * @return the player by nickname @param
     */
    public Player getPlayer(String nick) {
        return model.getPlayerEntity(nick);
    }

    /**
     * Return the entity of the player who is playing (it's his turn)
     *
     * @return the player who is playing the turn
     */
    public Player currentPlayer() {
        return model.getPlayersConnected().peek();
    }

    public void setNumPlayersToPlay(int numPlayersToPlay) {
        model.setNumPlayersToPlay(numPlayersToPlay);
    }

    public int getNumPlayersToPlay() {
        return model.getNumPlayersToPlay();
    }

    // Section: Overrides

    /**
     * Set the @param p player ready to start
     * When all the players are ready to start, the game starts (game status changes to running)
     *
     * @param nickname Player to set has ready
     * @return true if the game has started, false else
     */
    @Override
    public synchronized void setPlayerAsReadyToStart(String nickname) {
        model.setPlayerAsReadyToStart(getPlayerEntity(nickname));
    }

    /**
     * Gets the player entity
     * @param nickname
     * @return
     */
    public Player getPlayerEntity(String nickname){
        return model.getPlayerEntity(nickname);
    }

    /**
     * Check if the game is ready to start
     * @return true if the game is ready to start, false else
     */
    @Override
    public boolean isTheGameReadyToStart() {
        return model.arePlayersReadyToStartAndEnough();
    }

    // ***************************************** IMPORTANT *****************************************

    /**
     * Start the game if it's ready
     *
     */
    @Override
    public void startGame() throws IllegalStateException {
        if (!isTheGameReadyToStart()) {
            throw new IllegalStateException("The game is not ready to start");
        }
        else {
            System.out.println("Game " + this.model.getGameId() + " is starting...");
            extractFirstPlayerToPlay();
            model.initializeGame();
            model.setStatus(GameStatus.RUNNING);
        }
    }

    //***********************************************************************************************

    /**
     * Check if it's your turn
     *
     * @param nick the nickname of the player
     * @return true if it's your turn, false else
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public synchronized boolean isMyTurn(String nick) throws RemoteException {
        if (model.getPlayersConnected().isEmpty()) {
            return false;
        }
        else
            return model.getPlayersConnected().peek().getNickname().equals(nick);
    }

    /**
     * @return the number of online players
     * @throws RemoteException
     */
    @Override
    public int getNumConnectedPlayers() throws RemoteException {
        return model.getPlayersConnected().size();
    }

    //------------------------------------ logic management -----------------------------------------------


    public List<ObjectiveCard> getCommonObjectives() {
        return model.getCommonBoard().getCommonObjectives();
    }

    /**
     * Re-orders the players lists and sets the first player in both lists
     */
    private void extractFirstPlayerToPlay() {

        Player first_player = getPlayers().get(random.nextInt(getPlayers().size()));

        model.getAllPlayers().remove(first_player);
        model.getAllPlayers().addFirst(first_player);

        model.getPlayersConnected().remove(first_player);
        model.getPlayersConnected().addFirst(first_player);
    }

    /**
     * Return the status of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }

    // Section: Overrides

    /**
     * Place a card in the commonboard
     *
     * @param card_chosen the card to place
     * @param p           the player that places the card
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @throws RemoteException if there is an  error.
     */
    @Override
    public void placeCard(ResourceCard card_chosen, Player p, int x, int y)
            throws RemoteException {

        if (!getConnectedPlayers().contains(p))
            throw new RemoteException("Player not connected");
        else if (!isMyTurn(p.getNickname()))
            throw new RemoteException("It's not your turn");

        try {
            // Call the placeCard method in GameModel
            model.placeCard(card_chosen, p, x, y);
        } catch (IllegalMoveException e) {
            throw new RemoteException("Illegal move");
        }

        if (p.getCurrentPoints() >= DefaultValues.num_points_for_second_last_round) {
            model.setStatus(GameStatus.SECOND_LAST_ROUND);
        }
    }

    /**
     * Place a card in the commonboard
     *
     * @param card_chosen the card to place
     * @param p           the player that places the card
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @throws RemoteException if there is an  error.
     */
    @Override
    public void placeCard(GoldCard card_chosen, Player p, int x, int y)
            throws RemoteException {

        if (!getConnectedPlayers().contains(p))
            throw new RemoteException("Player not connected");
        else if (!isMyTurn(p.getNickname()))
            throw new RemoteException("It's not your turn");

        try {
            // Call the placeCard method in GameModel
            model.placeCard(card_chosen, p, x, y);
        } catch (IllegalMoveException e) {
            throw new RemoteException("Illegal move");
        }

        if (p.getCurrentPoints() >= DefaultValues.num_points_for_second_last_round) {
            model.setStatus(GameStatus.SECOND_LAST_ROUND);
        }
    }

    /**
     * Draw a card from the deck in commonBoard
     * @param player_nickname
     * @param index the index of the card to draw
     */
    @Override
    public void drawCard(String player_nickname, int index) {
        if (!(1 <= index && index <= 6))
            throw new IllegalArgumentException("invalid index");
        model.drawCard(getPlayerEntity(player_nickname), index);
        this.myTurnIsFinished();
    }

    /**
     * this method must be called every time a player finishes his/her turn,
     * i.e. whenever he/she has placed a card on his/her personal board and has also
     * drawn a new game card from the deck/table
     * @throws RuntimeException if the connection fails
     */
    @Override
    public void myTurnIsFinished() throws RuntimeException {
        try {
            model.nextTurn();
        } catch (GameEndedException e) {
            throw new RuntimeException(e);
        }
    }

    // game ended exception in draw card!!!

    /**
     * @return the ID of the game
     */
    @Override
    public int getGameId() {
        return model.getGameId();
    }

    public void performTurn(int column){

    }

//---------------------------------listeners management---------------------------------------

    /**
     * Add listener @param l to model listeners and player listeners
     *
     * @param l GameListener to add
     * @param p entity of the player
     */
    public void addListener(GameListener l, Player p) {

        model.addListener(l);
        Optional.ofNullable(model.getListeners()).ifPresent(listeners ->
                listeners.forEach(p::addListener)
        );

        getPlayers().stream()
                .filter(otherPlayer -> !otherPlayer.equals(p))
                .forEach(otherPlayer -> otherPlayer.addListener(l));
    }

    /**
     * Remove the listener @param lis to model listeners and player listeners
     *
     * @param lis GameListener to remove
     * @param p   entity of the player to remove
     */
    public void removeListener(GameListener lis, Player p) {

        model.removeListener(lis);
        Optional.ofNullable(p.getListeners()).ifPresent(List::clear);

        getPlayers().stream()
                .filter(otherPlayer -> !otherPlayer.equals(p))
                .forEach(otherPlayer -> otherPlayer.removeListener(lis));
    }

    //-------------------------------chat management----------------------------
    /**
     * Add a message to the chat list
     *
     * @param mess to add
     * @throws RemoteException if there is an error
     */
    @Override
    public synchronized void sendMessage(Message mess) throws RemoteException {
        model.sendMessage(mess);
    }


    // TODO: Check the following methods.
//    public void lastTurn() {
//        for (int i = 0; i < getNumPlayers(); i++) {
//            int current_player_index = model.getCurrentPlayer();
//            List<Player> players = model.getPlayers();
//            Player current_player = players.get(current_player_index);
//
//            assert current_player != null;
//            int prec_points = current_player.getPersonalBoard().getPoints();
//
//            placeCard(current_player.getChosenGameCard(), current_player.getPersonalBoard(),x, y);
//            int current_points = current_player.getPersonalBoard().getPoints();
//            int delta = current_points - prec_points;
//            model.getCommonBoard().movePlayer(current_player_index, delta);
//        }
//
//        model.setStatus(GameStatus.ENDED);
//    }
//
//    public void secondLastTurn() {
//        for (int i = model.getCommonBoard().getPartialWinner(); i < getNumPlayers(); i++)  {
//            int current_player_index = model.getCurrentPlayer();
//            List<Player> players = model.getPlayers();
//            Player current_player = players.get(current_player_index);
//
//            assert current_player != null;
//            int prec_points = current_player.getPersonalBoard().getPoints();
//            placeCard(current_player.getChosenGameCard(), current_player.getPersonalBoard(), coordinate, already_placed_card);
//            int current_points = current_player.getPersonalBoard().getPoints();
//            int delta = current_points - prec_points;
//            model.getCommonBoard().movePlayer(current_player_index, delta);
//            if(!model.getCommonBoard().getResourceConcreteDeck().isEmpty() && !model.getCommonBoard().getGoldConcreteDeck().isEmpty())
//                current_player.addToHand(drawCard(from_where_draw));
//        }
//        lastTurn();
//    }

}



