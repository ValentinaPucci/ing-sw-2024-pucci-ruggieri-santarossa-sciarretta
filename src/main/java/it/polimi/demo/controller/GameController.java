package it.polimi.demo.controller;

import it.polimi.demo.Constants;
import it.polimi.demo.network.utils.StaticPrinter;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.*;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.network.GameControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class GameController implements GameControllerInterface, Serializable, Runnable {

    @Serial
    private static final long serialVersionUID = -1535857999701952102L;
    private final Model model;
    private final Map<Listener, Ping> pings;

    /**
     * Constructor of the class
     * @param gameID the ID of the game
     * @param numberOfPlayers the number of players
     */
    public GameController(int gameID, int numberOfPlayers) {
        model = new Model(gameID, numberOfPlayers);
        pings = new HashMap<>();
        new Thread(this).start();
    }

    //------------------------------------ disconnection handling-----------------------------------------------

    /**
     * This method is called when the thread is started. It continuously checks for disconnections
     * of players by verifying the freshness of their pings.
     */
    @Override
    public void run() {
        Runnable checkDisconnections = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (model.getStatus() != GameStatus.WAIT) {
                    checkForDisconnections();
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        new Thread(checkDisconnections).start();
    }

    /**
     * Checks if any player is disconnected by verifying the freshness of their pings.
     * If a player's ping is expired, handle their disconnection.
     */
    public synchronized void checkForDisconnections() {
        pings.entrySet().stream()
                .filter(entry -> isPingFresh(entry.getValue()))
                .toList()
                .forEach(entry -> handleDisconnection(entry.getValue(), entry.getKey()));
    }

    /**
     * Determines if the given ping is expired based on the timeout.
     *
     * @param ping the ping to check
     * @return true if the ping is expired, false otherwise
     */
    private synchronized boolean isPingFresh(Ping ping) {
        return (System.currentTimeMillis() - ping.ping()) > Constants.secondsToWaitReconnection;
    }

    /**
     * Handles the disconnection of a player by removing them and notifying the system.
     *
     * @param ping the ping of the player
     * @param listener the listener of the player
     */
    public synchronized void handleDisconnection(Ping ping, Listener listener) {
        disconnectPlayer(ping.nickname(), listener, this::removeAndNotify);
    }

    /**
     * Adds a Ping to the list of pings to monitor player connections.
     *
     * @param nickname the player's nickname associated with the ping
     * @param listener the player's Listener associated with the ping
     */
    @Override
    public synchronized void addPing(String nickname, Listener listener) {
        if (model.arePlayersReadyToStartAndEnough())
            pings.put(listener, new Ping(System.currentTimeMillis(), nickname));
    }

    /**
     * Disconnects the player and performs a specified action during disconnection.
     *
     * @param nick the player's nickname
     * @param listener the player's Listener
     * @param disconnectAction the action to perform during disconnection
     */
    public synchronized void disconnectPlayer(String nick, Listener listener, BiConsumer<String, Listener> disconnectAction) {
        Player player = model.getIdentityOfPlayer(nick);
        if (player != null) {
            disconnectAction.accept(nick, listener);
            removeListener(listener);
        }
    }

    /**
     * Removes a listener and notifies the system about the player's disconnection.
     *
     * @param nick the player's nickname
     * @param listener the player's Listener
     */
    private synchronized void removeAndNotify(String nick, Listener listener) {
        removeListener(listener);
        model.removePlayer(model.getIdentityOfPlayer(nick));
        StaticPrinter.staticPrinter("Disconnection detected by ping of player: " + nick + " ");
        model.setStatus(GameStatus.ENDED);
        MainController.getControllerInstance().deleteGame(this.getGameId());
    }

    /**
     * This method is called when a player leaves the game
     * @param lis  the listener for updates
     * @param nick the nickname of the player leaving the game
     * @throws RemoteException if there is an error
     */
    @Override
    public synchronized void leave(Listener lis, String nick) throws RemoteException {
        Player p = model.getIdentityOfPlayer(nick);
        removeListener(lis);
        model.removePlayer(p);
        model.setStatus(GameStatus.ENDED);
    }

    @Override
    public void setError(String error){
        this.model.setErrorMessage(error);
    }

    //------------------------------------ management of players -----------------------------------------------

    /**
     * Add a player to the game
     *
     * @param p the player to offer
     * @throws PlayerAlreadyConnectedException if the player is already connected
     * @throws MaxPlayersLimitException if the maximum number of players is reached
     */
    public void addPlayer(Player p) throws PlayerAlreadyConnectedException, MaxPlayersLimitException {
        model.addPlayer(p);
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
     * Return the entity of the player who is playing (it's his turn)
     *
     * @return the player who is playing the turn
     */
    public Player getCurrentPlayer() {
        return model.getPlayersConnected().peek();
    }

    /**
     * Set the number of players to play
     * @param numPlayersToPlay the number of players to play
     */
    public void setNumPlayersToPlay(int numPlayersToPlay) {
        model.setNumPlayersToPlay(numPlayersToPlay);
    }

    /**
     * Get the number of players to play
     * @return the number of players to play
     */
    public int getNumPlayersToPlay() {
        return model.getNumPlayersToPlay();
    }

    // Section: Overrides

    /**
     * Gets the player identity from its nick
     * @param nickname the nickname of the player
     * @return the player identity
     */
    @Override
    public Player getIdentityOfPlayer(String nickname) {
        return model.getIdentityOfPlayer(nickname);
    }

    /**
     * check if it's the turn of the player
     * @param nick the nickname of the player
     * @return true if it's the turn of the player, false otherwise
     * @throws RemoteException if there is an error
     */
    @Override
    public synchronized boolean isMyTurn(String nick) throws RemoteException {
        return model.getPlayersConnected().peek().getNickname().equals(nick);
    }

    /**
     * Get the number of connected players
     *
     * @return the number of connected players
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public int getNumConnectedPlayers() throws RemoteException {
        return model.getPlayersConnected().size();
    }

    //------------------------------------ logic management -----------------------------------------------

    /**
     * Return the status of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }

    @Override
    public synchronized void playerIsReadyToStart(String nickname) {
        model.setPlayerAsReadyToStart(model.getIdentityOfPlayer(nickname));
    }

    /**
     * This method places the starter card in the first round of the game.
     * Note that, based on the rules, we need to call the end of our turn after
     * calling this method
     * @param nick the player which place the card
     * @param o the orientation of the card
     */
    @Override
    public void placeStarterCard(String nick, Orientation o) throws GameEndedException, RemoteException {
        model.placeStarterCard(getIdentityOfPlayer(nick), o);
    }

    /**
     * Choose a card from the hand
     * @param nick the player
     * @param which_card the index of the card to choose
     * @throws RemoteException if there is an error
     */
    @Override
    public void chooseCardFromHand(String nick, int which_card) throws RemoteException {
        model.chooseCardFromHand(getIdentityOfPlayer(nick), which_card);
    }


    /**
     * Show the personal board of the player
     * @param player_nickname the nickname of the player
     * @throws RemoteException if there is an error
     */
    @Override
    public void showOthersPersonalBoard(String player_nickname,int playerIndex) throws RemoteException {
        model.showOthersPersonalBoard(player_nickname, playerIndex);
    }

    /**
     * Place a card in the common board
     *
     * @param nick           the player that places the card
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @throws RemoteException if there is an  error.
     */
    @Override
    public void placeCard(String nick, int x, int y, Orientation orientation) throws RemoteException, GameEndedException {

        if (!getConnectedPlayers().contains(getIdentityOfPlayer(nick)))
            throw new RemoteException("Player not connected");
        else if (!isMyTurn(nick))
            throw new RemoteException("It's not your turn");

        if (orientation == Orientation.FRONT) {
            if (getIdentityOfPlayer(nick).getChosenGameCard() instanceof GoldCard) {
                model.placeCard((GoldCard) getIdentityOfPlayer(nick).getChosenGameCard(), getIdentityOfPlayer(nick), x, y);
            }
            else {
                model.placeCard(getIdentityOfPlayer(nick).getChosenGameCard(), getIdentityOfPlayer(nick), x, y);
            }
        } else {
            // does not matter whether the card is gold or resource only, the back is always resource!
            model.placeCard(getIdentityOfPlayer(nick).getChosenGameCard().getBack(), getIdentityOfPlayer(nick), x, y);
        }
    }

    /**
     * Draw a card from the deck in commonBoard. It also incorporates the logic of the game
     * with respect to the game dynamic, since it calls (through myTurnIsFinished) the nextTurn method in Model
     * @param player_nickname the nickname of the player
     * @param index the index of the card to draw
     */
    @Override
    public void drawCard(String player_nickname, int index) throws GameEndedException {
        model.drawCard(getIdentityOfPlayer(player_nickname), index);
    }

    /**
     * getter for the id of the game
     * @return the id of the game
     */
    @Override
    public int getGameId() {
        return model.getGameId();
    }

//---------------------------------listeners management--------------------------------------

    /**
     * Add a listener
     * @param l the listener to offer
     */
    public void addListener(Listener l) {
        model.addListener(l);
    }

    /**
     * Remove the listener
     * @param lis the listener to remove
     */
    public void removeListener(Listener lis) {
        model.removeListener(lis);
    }

    //-------------------------------chat management----------------------------

    /**
     * send a mex
     * @param nick the nickname of the player
     * @param mess the message to send
     * @throws RemoteException if there is an error
     */
    @Override
    public synchronized void sendMessage(String nick, Message mess) throws RemoteException {
        model.sendMessage(nick, mess);
    }

}


