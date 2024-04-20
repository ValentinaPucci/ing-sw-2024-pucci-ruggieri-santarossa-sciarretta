package it.polimi.demo.controller;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.*;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class GameController implements GameControllerInterface, Runnable, Serializable {

    /**
     * The {@link GameModel} to control
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

    //------------------------------------connection/reconnection management-----------------------------------------------

    /**
     * Override of the run method of the Runnable interface, checks the last ping if it is too old, the player is disconnected.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            // Here now we check if there are any disconnections
            synchronized (listeners_to_heartbeats) {

                for (Map.Entry<GameListener, Heartbeat> entry : listeners_to_heartbeats.entrySet()) {

                    GameListener listener = entry.getKey();
                    Heartbeat heartbeat = entry.getValue();
                    // If the heartbeat is not recent (i.e. the last ping was sent after the time-out), then the player is disconnected
                    if (System.currentTimeMillis() - heartbeat.getBeat() >
                            DefaultValues.timeout_for_detecting_disconnection) {
                        try {
                            disconnectPlayer(heartbeat.getPlayer(), listener);
                            printAsync("Disconnection detected by heartbeat of player: " + heartbeat.getPlayer().getNickname() + " ");

                            if (getNumConnectedPlayers() == 0) {
                                stopTimer();
                                // if no one is playing, we delete the game.
                                MainController.getInstance().deleteGame(getGameId());
                            }
                        } catch (RemoteException | GameEndedException e) {
                            throw new RuntimeException(e);
                        }
                        // Remove the disconnected player's heartbeat entry
                        listeners_to_heartbeats.remove(listener);
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Add a Ping to the list of listeners_to_heartbeats.
     *
     * Remark: this method is quite fundamental for the right behavior of the game, since we expect
     * to call it frequently to check if the players are still connected. In fact, looking at the run method,
     * it is clear that if the heartbeat related to a certain player is not 'fresh' in some sense, then indeed
     * the player will appear to us as disconnected.
     *
     * @param player the player's nickname associated to the heartbeat
     * @param me   the player's GameListener associated to the heartbeat
     * @throws RemoteException
     */
    @Override
    public synchronized void addPing(Player player, GameListener me) throws RemoteException {
        synchronized (listeners_to_heartbeats) {
            listeners_to_heartbeats.put(me, new Heartbeat(System.currentTimeMillis(), player));
        }
    }


    /**
     * Disconnect the player, if the game is in  status, the player is removed from the game
     * If only one player is connected, a timer of  will be started
     *
     * @param p        the player
     * @param listOfClient the listener of the client
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public void disconnectPlayer(Player p, GameListener listOfClient) throws RemoteException, GameEndedException {

        if (p != null && model.getPlayersConnected().contains(p)){
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
                        MainController.getInstance().deleteGame(model.getGameId());
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
     * @param p Player that want to reconnect
     * @throws PlayerAlreadyConnectedException if a player tries to rejoin the same game
     * @throws MaxPlayersLimitException    if there are already 4 players in game
     * @throws GameEndedException       the game is ended
     */
    public void reconnectPlayer(Player p) throws
            PlayerAlreadyConnectedException, MaxPlayersLimitException, GameEndedException, RemoteException {

        if (!model.getPlayersConnected().contains(p)) {
            model.reconnectPlayer(p);
            if (getNumConnectedPlayers() >= 2) {
                stopTimer();
            }
            // else nobody was connected and now one player has reconnected before the timer expires
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

    //------------------------------------ management of players -----------------------------------------------
    /**
     * Add player @param p to the Game
     *
     * @throws PlayerAlreadyConnectedException when in the game there is already another Player with the same nickname
     * @throws MaxPlayersLimitException    when the game has already reached its full capability
     */
    public void addPlayer(Player p) throws PlayerAlreadyConnectedException, MaxPlayersLimitException {
        model.addPlayer(p);
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

    /**
     * Start the game if it's ready
     */
    @Override
    public void startGame() {
        if (!isTheGameReadyToStart()) {
            throw new IllegalStateException("The game is not ready to start");
        }
        else {
            model.initializeGame();
            model.setStatus(GameStatus.RUNNING);
        }
    }

    /**
     * Check if it's your turn
     *
     * @param nick the nickname of the player
     * @return true if it's your turn, false else
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public synchronized boolean isThisMyTurn(String nick) throws RemoteException {
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

    /**
     * Return the secret Goal Card associated with the player in index @param indexPlayer
     *
     * @return CardGoal associated to the player
     */
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
        else if (!isThisMyTurn(p.getNickname()))
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
        else if (!isThisMyTurn(p.getNickname()))
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
     * requires 1 <= index <= 3
     */
    @Override
    public void drawCard(int index) {
        if (!(1 <= index && index <= 6))
            throw new IllegalArgumentException("invalid index");
        model.drawCard(index);
    }

    @Override
    public void myTurnIsFinished() {
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

//---------------------------------listeners management---------------------------------------

    /**
     * Add listener @param l to model listeners and player listeners
     *
     * @param l GameListener to add
     * @param p entity of the player
     */
    public void addListener(GameListener l, Player p) {
        
        model.addListener(l);
        
        for (GameListener othersListener : model.getListeners()) {
            p.addListener(othersListener);
        }
        for (Player otherPlayer : getPlayers()) {
            if (!otherPlayer.equals(p)) {
                otherPlayer.addListener(l);
            }
        }
    }

    /**
     * Remove the listener @param lis to model listeners and player listeners
     *
     * @param lis GameListener to remove
     * @param p   entity of the player to remove
     */
    public void removeListener(GameListener lis, Player p) {
        
        model.removeListener(lis);
        p.getListeners().clear();

        for (Player otherPlayer : getPlayers()) {
            if (!otherPlayer.equals(p)) {
                otherPlayer.removeListener(lis);
            }
        }
    }

    //-------------------------------chat management----------------------------
    /**
     * Add a message to the chat list
     *
     * @param msg to add
     * @throws RemoteException if there is an error
     */
    @Override
    public synchronized void sendMessage(Message msg) throws RemoteException {
        model.sendMessage(msg);
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



