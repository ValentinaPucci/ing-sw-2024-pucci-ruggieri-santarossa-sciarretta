package it.polimi.demo.network;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

// todo: javadocs checked, they are ok!

/**
 * GameControllerInterface provides the methods required for controlling the game via RMI.
 */
public interface GameControllerInterface extends Remote {

    /**
     * Places the starter card for the player.
     *
     * @param nickname    the nickname of the player
     * @param orientation the orientation of the starter card
     * @throws GameEndedException if the game has already ended
     * @throws RemoteException    if an RMI error occurs
     */
    void placeStarterCard(String nickname, Orientation orientation) throws GameEndedException, RemoteException;

    /**
     * Chooses a card from the player's hand.
     *
     * @param nick  the nickname of the player
     * @param index the index of the card to choose
     * @throws RemoteException if an RMI error occurs
     */
    void chooseCardFromHand(String nick, int index) throws RemoteException;

    /**
     * Places a card on the board.
     *
     * @param nickname    the nickname of the player
     * @param x           the x-coordinate on the board
     * @param y           the y-coordinate on the board
     * @param orientation the orientation of the card
     * @throws RemoteException    if an RMI error occurs
     * @throws GameEndedException if the game has already ended
     */
    void placeCard(String nickname, int x, int y, Orientation orientation) throws RemoteException, GameEndedException;

    /**
     * Draws a card for the player.
     *
     * @param playerNickname the nickname of the player
     * @param index          the index of the card to draw
     * @throws RemoteException    if an RMI error occurs
     * @throws GameEndedException if the game has already ended
     */
    void drawCard(String playerNickname, int index) throws RemoteException, GameEndedException;

    /**
     * Checks if it is the player's turn.
     *
     * @param nick the nickname of the player
     * @return true if it is the player's turn, false otherwise
     * @throws RemoteException if an RMI error occurs
     */
    boolean isMyTurn(String nick) throws RemoteException;

    /**
     * Gets the identity of a player.
     *
     * @param nickname the nickname of the player
     * @return the Player object representing the player
     * @throws RemoteException if an RMI error occurs
     */
    Player getIdentityOfPlayer(String nickname) throws RemoteException;

    /**
     * Gets the game ID.
     *
     * @return the game ID
     * @throws RemoteException if an RMI error occurs
     */
    int getGameId() throws RemoteException;

    /**
     * Gets the list of connected players.
     *
     * @return a LinkedList of connected players
     * @throws RemoteException if an RMI error occurs
     */
    LinkedList<Player> getConnectedPlayers() throws RemoteException;

    /**
     * Gets the current player.
     *
     * @return the Player object representing the current player
     * @throws RemoteException if an RMI error occurs
     */
    Player getCurrentPlayer() throws RemoteException;

    /**
     * Sends a message in the game.
     *
     * @param nick the nickname of the player sending the message
     * @param mess the message to send
     * @throws RemoteException if an RMI error occurs
     */
    void sendMessage(String nick, Message mess) throws RemoteException;

    /**
     * Shows the personal board of another player.
     *
     * @param playerNickname the nickname of the player requesting to see another's board
     * @param playerIndex    the index of the player whose board is to be shown
     * @throws RemoteException if an RMI error occurs
     */
    void showOthersPersonalBoard(String playerNickname, int playerIndex) throws RemoteException;

    /**
     * Gets the number of connected players.
     *
     * @return the number of connected players
     * @throws RemoteException if an RMI error occurs
     */
    int getNumConnectedPlayers() throws RemoteException;

    /**
     * Leaves the game.
     *
     * @param lis  the listener for updates
     * @param nick the nickname of the player leaving the game
     * @throws RemoteException if an RMI error occurs
     */
    void leave(Listener lis, String nick) throws RemoteException;

    /**
     * Sets an error message.
     *
     * @param s the error message
     * @throws RemoteException if an RMI error occurs
     */
    void setError(String s) throws RemoteException;

    /**
     * Gets the status of the game.
     *
     * @return the GameStatus
     * @throws RemoteException if an RMI error occurs
     */
    GameStatus getStatus() throws RemoteException;

    /**
     * Marks the player as ready to start the game.
     *
     * @param nickname the nickname of the player
     * @throws RemoteException if an RMI error occurs
     */
    void playerIsReadyToStart(String nickname) throws RemoteException;

    /**
     * Adds a ping to keep the connection alive.
     *
     * @param nickname the nickname of the player
     * @param listener the listener for updates
     * @throws RemoteException if an RMI error occurs
     */
    void addPing(String nickname, Listener listener) throws RemoteException;

    /**
     * Gets the number of players needed to start the game.
     *
     * @return the number of players to play
     * @throws RemoteException if an RMI error occurs
     */
    int getNumPlayersToPlay() throws RemoteException;
}
