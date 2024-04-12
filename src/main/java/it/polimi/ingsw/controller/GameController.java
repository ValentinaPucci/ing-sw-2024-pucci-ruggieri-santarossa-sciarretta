package it.polimi.ingsw.controller;


import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.networking.rmi.remoteInterfaces.GameControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.ingsw.networking.PrintAsync.printAsync;

public class GameController implements GameControllerInterface, Runnable{
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
     * Reconnect the player with the nickname @param to the game
     *
     * @param p Player that want to reconnect
     * @throws PlayerAlreadyConnectedException if a player tries to rejoin the same game
     * @throws MaxPlayersLimitException    if there are already 4 players in game
     * @throws GameEndedException       the game is ended
     */
    public void reconnectPlayer(Player p) throws PlayerAlreadyConnectedException, MaxPlayersLimitException, GameEndedException {
        boolean outputres = model.reconnectPlayer(p);

        if (outputres && getNumOfOnlinePlayers() > 1) {
            stopReconnectionTimer();
        }
        //else nobody was connected and now one player has reconnected before the timer expires
    }

    /**
     * Returns num of current players that are in the game (online and offline)
     *
     * @return num of current players
     */
    public int getNumOfPlayers() {
        return model.getNumOfPlayers();
    }

    /**
     * @return the number of online players that are in the game
     */
    public int getNumOfOnlinePlayers() {
        return model.getNumOfOnlinePlayers();
    }

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
        int ris = random.nextInt(model.getNumOfPlayers());
        model.setFirstTurnIndex(ris);
        model.setCurrentPlayer(ris);
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

//TODO: ADATTARE AL PLACE CARD
//
//    public synchronized void positionTileOnShelf(String p, int column, TileType type) {
//        if (isPlayerTheCurrentPlaying(model.getPlayerEntity(p))) {
//
//            Player currentPlaying = this.whoIsPlaying();//Because position can call nextTurn
//            int currentPlayingIndex = this.getIndexCurrentPlaying();
//
//            try {
//                model.positionTileOnShelf(model.getPlayerEntity(p), column, type);
//
//                checkCommonCards(currentPlaying);
//
//
//                if (currentPlaying.getShelf().getFreeSpace() == 0 && (!model.getStatus().equals(GameStatus.LAST_CIRCLE) && !model.getStatus().equals(GameStatus.ENDED))) {
//                    //This player has his shelf full, time to complete le last circle
//                    model.setStatus(GameStatus.LAST_CIRCLE);
//                    model.setFinishedPlayer(currentPlayingIndex);
//                    currentPlaying.addPoint(new Point(true),model);
//                }
//
//                //if the hand is empty then call next turn
//                if (currentPlaying.getInHandTile().size() == 0) {
//                    model.nextTurn();
//                }
//
//            } catch (GameEndedException e) {
//                //Time to check for personal goal and final
//                checkGoalCards();
//                checkFinal();
//                model.setStatus(GameStatus.ENDED);
//            }
//
//        } else {
//            throw new NotPlayerTurnException();
//        }
//
//    }

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

                    if (model.getNumOfOnlinePlayers() == 0) {
                        //No players online, I delete the games
                        MainController.getInstance().deleteGame(model.getGameId());
                    } else if (model.getNumOfOnlinePlayers() == 1) {
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
     * Return the {@link GameStatus} of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }


//--------------override automatico-------------------------------------------------------

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            //checks all the heartbeat to detect disconnection
            if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_TURN) || model.getStatus().equals(GameStatus.ENDED) || model.getStatus().equals(GameStatus.WAIT)) {
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


    @Override
    public void extractCardFromCommonBoard(int x, int y, String p) throws RemoteException {

    }

    //TODO: adattare!!!!!!!!!!
    @Override
    public void placeCard(String p) throws RemoteException, GameEndedException {

    }

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
            if ((model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_TURN)) && model.getNumOfOnlinePlayers() == 1) {
                //Starting a th for waiting until reconnection at least of 1 client to keep playing
                if (reconnectionTh == null) {
                    startReconnectionTimer();
                    printAsync("Starting timer for reconnection waiting: " + DefaultValues.secondsToWaitReconnection + " seconds");
                }
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

    /**
     * @return the ID of the game
     */
    @Override
    public int getGameId() {
        return model.getGameId();
    }

    /**
     * @return the number of online players
     * @throws RemoteException
     */
    @Override
    public int getNumOnlinePlayers() throws RemoteException {
        return model.getNumOfOnlinePlayers();
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
}
