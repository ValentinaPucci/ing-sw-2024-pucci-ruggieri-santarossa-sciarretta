package it.polimi.demo.controller;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.*;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class GameController implements GameControllerInterface, Serializable, Runnable {

    /**
     * The model to control
     */
    private GameModel model;

    private final Map<GameListener, Heartbeat>  heartbeats;

    public GameController(int gameID, int numberOfPlayers, Player player) {
        model = new GameModel(gameID, numberOfPlayers, player);
        heartbeats = new HashMap<>();
        // todo: make this call to work
        // new Thread(this).start();
    }

    //------------------------------------ disconnection handling-----------------------------------------------

    /**
     * This method is called when the thread is started.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (model.getStatus() != GameStatus.WAIT) {
                checkForDisconnections();
            }
        }
    }
    /**
     * Check if a player is disconnected by checking the heartbeat freshness, if it is expired, the player is disconnected
     * and handleDisconnection will deal with it.
     */
    public void checkForDisconnections() {
        synchronized (heartbeats) {
            for (Map.Entry<GameListener, Heartbeat> entry : heartbeats.entrySet()) {
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
     * @param heartbeat the heartbeat to check
     * @return true if the heartbeat is expired, false else
     */
    private boolean isHeartbeatExpired(Heartbeat heartbeat) {
        long currentTime = System.currentTimeMillis();
        long lastBeatTime = heartbeat.getPing();
        return currentTime - lastBeatTime > DefaultValues.secondsToWaitReconnection;
    }

    public void handleDisconnection(Heartbeat heartbeat, GameListener listener) {
        try {
            disconnectPlayer(heartbeat.getNick(), listener);
            printAsync("Disconnection detected by heartbeat of player: " + heartbeat.getNick() + " ");
            if (this.getNumConnectedPlayers() < 2) {
                printAsync("Game ended because only one player is connected");
                MainController.getControllerInstance().deleteGame(this.getGameId());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a Ping to the list of heartbeats.
     * Remark: this method is quite fundamental for the right behavior of the game, since we expect
     * to call it frequently to check if the players are still connected. In fact, looking at the run method,
     * it is clear that if the heartbeat related to a certain player is not 'fresh' in some sense, then indeed
     * the player will appear to us as disconnected.
     *
     * @param nickname the player's nickname associated to the heartbeat
     * @param me   the player's GameListener associated to the heartbeat
     */
    @Override
    public synchronized void addPing(String nickname, GameListener me) {
        synchronized (heartbeats) {
            heartbeats.put(me, new Heartbeat(System.currentTimeMillis(), nickname));
        }
    }

    /**
     * Disconnect the player, if the game is in status, the player is removed from the game
     * If only one player is connected, a timer of  will be started
     *
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public void disconnectPlayer(String nick, GameListener lisOfClient) throws RemoteException {
        Player p = model.getPlayerEntity(nick);
        if (p != null) {
            removeListener(lisOfClient, p);
            model.removePlayer(p);
        }
    }

    @Override
    public void reconnectPlayer(Player p) throws RemoteException {

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
    public void addPlayer(Player p) throws PlayerAlreadyConnectedException, MaxPlayersLimitException {
        this.model.addPlayer(p);
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
     * @param nick the nickname of the player
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
    public Player getCurrentPlayer() {
        return model.getPlayersConnected().peek();
    }

    public void setNumPlayersToPlay(int numPlayersToPlay) {
        model.setNumPlayersToPlay(numPlayersToPlay);
    }

    public int getNumPlayersToPlay() {
        return model.getNumPlayersToPlay();
    }

    // Section: Overrides

    @Override
    public synchronized void setPlayerAsConnected(Player p) {
        // model.setPlayerAsConnected(p);
    }

    /**
     * Gets the player entity
     * @param nickname the nickname of the player
     * @return the player entity
     */
    public Player getPlayerEntity(String nickname) {
        return model.getPlayerEntity(nickname);
    }

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
     * Return the status of the model
     *
     * @return status
     */
    public GameStatus getStatus() {
        return model.getStatus();
    }

    @Override
    public synchronized void playerIsReadyToStart(String nickname) {
        model.setPlayerAsReadyToStart(model.getPlayerEntity(nickname));
    }

    @Override
    public boolean isThisMyTurn(String nickname) {
        return model.getPlayersConnected().peek().getNickname().equals(nickname);
    }

    /**
     * This method places the starter card in the first round of the game.
     * Note that, based on the rules, we need to call the end of our turn after
     * calling this method
     * @param nick the player which place the card
     * @param o the orientation of the card
     */
    @Override
    public void placeStarterCard(String nick, Orientation o) throws GameEndedException {
        model.placeStarterCard(getPlayerEntity(nick), o);
    }

    /**
     * Choose a card from the hand
     * @param nick the player
     * @param which_card the index of the card to choose
     * @throws RemoteException if there is an error
     */
    @Override
    public void chooseCardFromHand(String nick, int which_card) throws RemoteException {
        model.chooseCardFromHand(getPlayerEntity(nick), which_card);
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

        if (!getConnectedPlayers().contains(getPlayerEntity(nick)))
            throw new RemoteException("Player not connected");
        else if (!isMyTurn(getPlayerEntity(nick).getNickname()))
            throw new RemoteException("It's not your turn");

        if (orientation == Orientation.FRONT) {
            if (getPlayerEntity(nick).getChosenGameCard() instanceof GoldCard) {
                model.placeCard((GoldCard) getPlayerEntity(nick).getChosenGameCard(), getPlayerEntity(nick), x, y);
            }
            else {
                model.placeCard(getPlayerEntity(nick).getChosenGameCard(), getPlayerEntity(nick), x, y);
            }
        } else {
            // does not matter whether the card is gold or resource only, the back is always resource!
            model.placeCard(getPlayerEntity(nick).getChosenGameCard().getBack(), getPlayerEntity(nick), x, y);
        }
    }

    /**
     * Draw a card from the deck in commonBoard. It also incorporates the logic of the game
     * with respect to the game flow, since it calls (through myTurnIsFinished) the nextTurn method in GameModel
     * @param player_nickname the nickname of the player
     * @param index the index of the card to draw
     */
    @Override
    public void drawCard(String player_nickname, int index) throws GameEndedException {
        model.drawCard(getPlayerEntity(player_nickname), index);
    }


    /**
     * @return the ID of the game
     */
    @Override
    public int getGameId() {
        return model.getGameId();
    }



//---------------------------------listeners management--------------------------------------

    /**
     * Add listener @param l to model listeners and player listeners
     *
     * @param l GameListener to add
     * @param p entity of the player
     */
    public void addListener(GameListener l, Player p) {
        model.addListener(l);
//        for (GameListener othersListener : model.getListeners()) {
//            p.addListener(othersListener);
//        }
//        for (Player otherPlayer : model.getAllPlayers()) {
//            if (!otherPlayer.equals(p)) {
//                otherPlayer.addListener(l);
//            }
//        }
    }

    /**
     * Remove the listener @param lis to model listeners and player listeners
     *
     * @param lis GameListener to remove
     * @param p   entity of the player to remove
     */
    public void removeListener(GameListener lis, Player p) {
        model.removeListener(lis);
//        Optional.ofNullable(p.getListeners()).ifPresent(List::clear);
//        getPlayers().stream()
//                .filter(otherPlayer -> !otherPlayer.equals(p))
//                .forEach(otherPlayer -> otherPlayer.removeListener(lis));
    }

    //-------------------------------chat management----------------------------

    /**
     * Add a message to the chat list
     *
     * @param mess to add
     * @throws RemoteException if there is an error
     */
    @Override
    public synchronized void sendMessage(String nick, Message mess) throws RemoteException {
        model.sendMessage(nick, mess);
    }

}


