package it.polimi.demo.networking.rmi.remoteInterfaces;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * This interface contains all the action a player can do in a single game */
public interface GameControllerInterface extends Remote {

    void placeStarterCard(String nickname, Orientation orientation) throws GameEndedException, RemoteException;

    void chooseCardFromHand(String nick, int index) throws RemoteException;

    /**
     * This method place a RESOURCECARD in the commonboard.
     * @param nickname the player that place the card
     * @param x the x coordinate of the card on his/her personal board
     * @param y the y coordinate of the card on his/her personal board
     * @throws RemoteException if the connection fails
     */
    void placeCard(String nickname, int x, int y, Orientation orientation) throws RemoteException, GameEndedException;


    /**
     * Draw a card from the deck in commonBoard
     * @param player_nickname
     * @param index the index of the card to draw
     */
    void drawCard(String player_nickname, int index) throws RemoteException, GameEndedException;

    /**
     * This method checks if it's the turn of player named 'nick'.
     * @param nick the nickname of the player
     * @return true if it's the turn of the player
     * @throws RemoteException if the connection fails
     */
    boolean isMyTurn(String nick) throws RemoteException;

    /**
     * Gets the player entity
     * @param nickname
     * @return
     */
    Player getPlayerEntity(String nickname) throws RemoteException;

    /**
     * This method returns the id of the game
     * @return the id of the game
     * @throws RemoteException if the connection fails
     */
    int getGameId() throws RemoteException;

    /**
     * Set the player as connected
     * @param p the player to set as connected
     */
    void setPlayerAsConnected(Player p) throws RemoteException;

    LinkedList<Player> getConnectedPlayers() throws RemoteException;

    Player getCurrentPlayer() throws RemoteException;


     void disconnectPlayer(String nick, GameListener lisOfClient) throws RemoteException;

    void reconnectPlayer(Player p) throws RemoteException;

    /**
     * Add a message to the chat list
     * @param mess the message to send {@link Message}
     * @throws RemoteException if the connection fails
     */
    void sendMessage(String nick, Message mess) throws RemoteException;

    /**
     * This method return the number of the online players
     * @return the number of the online players
     * @throws RemoteException if the connection fails
     */
    int getNumConnectedPlayers() throws RemoteException;

    /**
     * It removes a player by nickname @param nick from the game including the associated listeners
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @throws RemoteException if the connection fails
     */
    void leave(GameListener lis, String nick) throws RemoteException;

    void setError(String s) throws RemoteException;

    GameStatus getStatus() throws RemoteException;

    void playerIsReadyToStart(String nickname) throws RemoteException;

    boolean isThisMyTurn(String nickname) throws RemoteException;

    void addPing(String nickname, GameListener gameListener) throws RemoteException;

    int getNumPlayersToPlay() throws RemoteException;
}