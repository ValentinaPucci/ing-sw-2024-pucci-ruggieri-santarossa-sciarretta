package it.polimi.demo.controller;


import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.*;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;

import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class GameController implements GameControllerInterface, Runnable {
    /**
     * The {@link GameModel} to control
     */
    private GameModel model;

    /**
     * A random object for implementing pseudo-random choice
     */
    private final Random random = new Random();

    /**
     * Map of heartbeats for detecting disconnections
     * For implementing AF: "Clients disconnections"
     */
    private final Map<GameListener, Heartbeat> heartbeats;

    /**
     * Timer started when only one player is playing
     * it ends the game if no one reconnects within seconds
     */
    private Thread reconnectionTh;

    public GameController() {
        model = new GameModel();
        heartbeats = new HashMap<>();
        new Thread(this).start();
    }

    //------------------------------------ gestione players -----------------------------------------------
    /**
     * Add player @param p to the Game
     * <br>
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
        return model.getPlayers();
    }


    /**
     * Returns num of current players that are in the game (online and offline)
     *
     * @return num of current players
     */
    public int getNumPlayers() {
        return model.getNumPlayers();
    }


    /**
     * Set the @param p player ready to start
     * When all the players are ready to start, the game starts (game status changes to running)
     *
     * @param p Player to set has ready
     * @return true if the game has started, false else
     */
    public synchronized boolean playerIsReadyToStart(String p) {
        //La partita parte automaticamente quando tutti i giocatori sono pronti
        model.playerIsReadyToStart(model.getPlayerEntity(p));

        if (model.arePlayersReadyToStartAndEnough()) {
            model.initializeGame();
            model.setStatus(GameStatus.RUNNING);
            return true;
        }

        return false;//Game non started yet
    }

    /**
     * Return the index of the player who is currently playing the turn
     *
     * @return index of the player who is moving
     */
    public int getIndexCurrentPlayer() {
        return model.getCurrentPlayer();
    }

    /**
     * Return the player who is currently playing the turn
     *
     * @param p the player that you want to know if is the current playing
     * @return true if the player is the current playing, false else
     */
    private boolean isPlayerTheCurrentPlaying(Player p) {
        return whoIsPlaying().equals(p);
    }
    /**
     * Return the entity of the player associated with the nickname @param
     *
     * @param playerNick
     * @return the player by nickname @param
     */
    public Player getPlayer(String playerNick) {
        return model.getPlayerEntity(playerNick);
    }

    /**
     * Return the entity of the player who is playing (it's his turn)
     *
     * @return the player who is playing the turn
     */
    public Player whoIsPlaying() {
        return model.getPlayers().get(model.getCurrentPlayer());
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
        return model.getPlayers().get(model.getCurrentPlayer()).getNickname().equals(nick);
    }


    /**
     * @return the number of online players
     * @throws RemoteException
     */
    @Override
    public int getNumOnlinePlayers() throws RemoteException {
        return model.getNumOnlinePlayers();
    }

    //------------------------------------ gestione riconnessioni -----------------------------------------------
    /**
     * Reconnect the player with the nickname @param to the game
     *
     * @param p Player that want to reconnect
     * @throws PlayerAlreadyConnectedException if a player tries to rejoin the same game
     * @throws MaxPlayersLimitException    if there are already 4 players in game
     * @throws GameEndedException       the game is ended
     */
    public void reconnectPlayer(Player p) throws PlayerAlreadyConnectedException, MaxPlayersLimitException, GameEndedException, RemoteException {
        boolean outputres = model.reconnectPlayer(p);

        // Why 1?
        if (outputres && (getNumOnlinePlayers() > 1)) {
            stopReconnectionTimer();
        }
        //else nobody was connected and now one player has reconnected before the timer expires
    }


    /**
     * Starts a timer for detecting the reconnection of a player, if no one reconnects in time, the game is over
     */
    @SuppressWarnings("BusyWait")
    private void startReconnectionTimer() {
        reconnectionTh = new Thread(
                () -> {
                    long startingtimer = System.currentTimeMillis();

                    while (reconnectionTh != null && !reconnectionTh.isInterrupted() && System.currentTimeMillis() - startingtimer < DefaultValues.secondsToWaitReconnection * 1000) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            //Someone called interrupt on this th (no need to keep waiting)
                        }
                    }
                    printAsync("Timer for reconnection ended");

                    if (model.getNumOnlinePlayers() == 0) {
                        //No players online, I delete the games
                        MainController.getInstance().deleteGame(model.getGameId());
                    } else if (model.getNumOnlinePlayers() == 1) {
                        printAsync("\tNo player reconnected on time, set game to ended!");
                        model.setStatus(GameStatus.ENDED);
                    } else {
                        printAsync("\tA player reconnected on time");
                        this.reconnectionTh = null;
                    }
                }

        );
        reconnectionTh.start();
    }


    /**
     * It stops the timer (if started) that checks for reconnections of players
     */
    private void stopReconnectionTimer() {
        if (reconnectionTh != null) {
            reconnectionTh.interrupt();
            reconnectionTh = null;
        }
        //else It means that a player reconnected but the timer was not started (ex 3 players and 1 disconnects)
    }


    /**
     * Disconnect the player, if the game is in  status, the player is removed from the game
     * If only one player is connected, a timer of  will be started
     *
     * @param nick        the nickname of the player
     * @param lisOfClient the listener of the client
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public void disconnectPlayer(String nick, GameListener lisOfClient) throws RemoteException {

        //Player has just disconnected, so I remove the notifications for him
        Player p = model.getPlayerEntity(nick);
        if(p!=null) {
            removeListener(lisOfClient, p);

            if (model.getStatus().equals(GameStatus.WAIT)) {
                //The game is in Wait (game not started yet), the player disconnected, so I remove him from the game)
                model.removePlayer(nick);
            } else {
                //Tha game is running, so I set him as disconnected (He can reconnect soon)
                model.setAsDisconnected(nick);
            }

            //Check if there is only one player playing
            if ((model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_ROUND)) && model.getNumOnlinePlayers() == 1) {
                //Starting a th for waiting until reconnection at least of 1 client to keep playing
                if (reconnectionTh == null) {
                    startReconnectionTimer();
                    printAsync("Starting timer for reconnection waiting: " + DefaultValues.secondsToWaitReconnection + " seconds");
                }
            }
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
        model.removePlayer(nick);
    }


    //------------------------------------ gestione logica -----------------------------------------------

    /**
     * Return the secret Goal Card associated with the player in index @param indexPlayer
     *
     * @return CardGoal associated to the player
     */
    public ObjectiveCard[] getCommonObjectives() {
        return model.getCommonBoard().getCommonObjectives();
    }

    /**
     * Extract pseudo-randomly the player who has the first move (first turn)
     */
    private void extractFirstTurn() {
        int ris = random.nextInt(model.getNumPlayers());
        model.setFirstTurnIndex(ris);
        model.setCurrentPlayer(getPlayers().get(ris));
    }

    /**
     * Return the {@link GameStatus} of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }


    /**
     * Draw a card from the deck
     * @param index
     * @return
     * @throws RemoteException
     */
    @Override
    public Card drawCard(int index) throws RemoteException {
        return model.drawCard(index);
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
    public void placeCard(ResourceCard card_chosen, Player p,int x, int y) throws RemoteException{
        try {
            // Retrieve the Player object using the provided nickname
            if (p == null) {
                throw new RemoteException("Player not found");
            }
            // Call the placeCard method in GameModel
            model.placeCard(card_chosen, p, x, y);
        } catch (IllegalMoveException e) {
            throw new RemoteException("Illegal move", e);
        }
    }

    /**
     * @return the ID of the game
     */
    @Override
    public int getGameId() {
        return model.getGameId();
    }


//--------------gestione heartbeats-------------------------------------------------------

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            //checks all the heartbeat to detect disconnection
            if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_ROUND) || model.getStatus().equals(GameStatus.ENDED) || model.getStatus().equals(GameStatus.WAIT)) {
                synchronized (heartbeats) {
                    Iterator<Map.Entry<GameListener, Heartbeat>> heartIter = heartbeats.entrySet().iterator();

                    while (heartIter.hasNext()) {
                        Map.Entry<GameListener, Heartbeat> el = (Map.Entry<GameListener, Heartbeat>) heartIter.next();
                        if (System.currentTimeMillis() - el.getValue().getBeat() > DefaultValues.timeout_for_detecting_disconnection) {
                            try {
                                this.disconnectPlayer(el.getValue().getNick(), el.getKey());
                                printAsync("Disconnection detected by heartbeat of player: "+el.getValue().getNick()+"");

                                if (this.getNumOnlinePlayers() == 0) {
                                    stopReconnectionTimer();
                                    MainController.getInstance().deleteGame(this.getGameId());
                                    return;
                                }

                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }

                            heartIter.remove();
                        }
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
     * Add a hearthbeat to the list of heartbeats
     *
     * @param nick the player's nickname associated to the heartbeat
     * @param me   the player's GameListener associated to the heartbeat
     * @throws RemoteException
     */
    @Override
    public synchronized void heartbeat(String nick, GameListener me) throws RemoteException {
        synchronized (heartbeats) {
            heartbeats.put(me, new Heartbeat(System.currentTimeMillis(), nick));
        }
    }

//---------------------------------gestione listeners---------------------------------------

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
        for (Player otherPlayer : model.getPlayers()) {
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

        for (Player otherPlayer : model.getPlayers()) {
            if (!otherPlayer.equals(p)) {
                otherPlayer.removeListener(lis);
            }
        }
    }



    //-------------------------------gestione chat----------------------------
    /**
     * Add a message to the chat list
     *
     * @param msg to add
     * @throws RemoteException
     */
    @Override
    public synchronized void sentMessage(Message msg) throws RemoteException {
        model.sentMessage(msg);
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



