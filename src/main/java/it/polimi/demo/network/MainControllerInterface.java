package it.polimi.demo.network;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

// todo: javadocs checked, they are ok!

/**
 * MainControllerInterface provides the methods required for game control via RMI.
 */
public interface MainControllerInterface extends Remote {

    /**
     * Creates a new game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player creating the game
     * @param numPlayers the number of players for the game
     * @return the GameControllerInterface for the created game
     * @throws RemoteException if an RMI error occurs
     */
    GameControllerInterface createGame(Listener lis, String nick, int numPlayers) throws RemoteException;

    /**
     * Joins an existing game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player joining the game
     * @param idGame the ID of the game to join
     * @return the GameControllerInterface for the joined game
     * @throws RemoteException if an RMI error occurs
     */
    GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException;

    /**
     * Joins a game randomly.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player joining the game
     * @return the GameControllerInterface for the joined game
     * @throws RemoteException if an RMI error occurs
     */
    GameControllerInterface joinRandomly(Listener lis, String nick) throws RemoteException;

    /**
     * Sets a player as ready in the specified game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     */
    void setAsReady(Listener lis, String nick, int idGame) throws RemoteException;

    /**
     * Places the starter card for a player in the specified game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player
     * @param o the orientation of the starter card
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     * @throws GameEndedException if the game has already ended
     */
    void placeStarterCard(Listener lis, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException;

    /**
     * Chooses a card for a player in the specified game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player
     * @param cardIndex the index of the card to choose
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     * @throws GameEndedException if the game has already ended
     */
    void chooseCard(Listener lis, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException;

    /**
     * Places a card on the board for a player in the specified game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player
     * @param x the x-coordinate on the board
     * @param y the y-coordinate on the board
     * @param o the orientation of the card
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     * @throws GameEndedException if the game has already ended
     */
    void placeCard(Listener lis, String nick, int x, int y, Orientation o, int idGame) throws RemoteException, GameEndedException;

    /**
     * Draws a card for a player in the specified game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player
     * @param index the index of the card to draw
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     * @throws GameEndedException if the game has already ended
     */
    void drawCard(Listener lis, String nick, int index, int idGame) throws RemoteException, GameEndedException;

    /**
     * Sends a message in the specified game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player sending the message
     * @param message the message to send
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     */
    void sendMessage(Listener lis, String nick, Message message, int idGame) throws RemoteException;

    /**
     * Shows the personal board of another player in the specified game.
     *
     * @param lis the listener for updates
     * @param nickname the nickname of the player requesting to see another's board
     * @param playerIndex the index of the player whose board is to be shown
     * @param gameId the ID of the game
     * @throws RemoteException if an RMI error occurs
     */
    void showOthersPersonalBoard(Listener lis, String nickname, int playerIndex, int gameId) throws RemoteException;

    /**
     * Adds a ping to keep the connection alive.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     */
    void addPing(Listener lis, String nick, int idGame) throws RemoteException;

    /**
     * Leaves the specified game.
     *
     * @param lis the listener for updates
     * @param nick the nickname of the player leaving the game
     * @param idGame the ID of the game
     * @throws RemoteException if an RMI error occurs
     */
    void leaveGame(Listener lis, String nick, int idGame) throws RemoteException;

    /**
     * Gets the game controller for the specified game.
     *
     * @param idGame the ID of the game
     * @return the GameControllerInterface for the specified game
     * @throws RemoteException if an RMI error occurs
     */
    GameControllerInterface getGameController(int idGame) throws RemoteException;
}
