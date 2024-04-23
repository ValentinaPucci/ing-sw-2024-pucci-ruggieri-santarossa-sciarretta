package it.polimi.demo.networking.rmi;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains all the action a player can do in a single game */
public interface GameControllerInterface extends Remote {

    /**
     * This method place a RESOURCECARD in the commonboard.
     * @param card_chosen the card to place
     * @param p the player that place the card
     * @param x the x coordinate of the card on his/her personal board
     * @param y the y coordinate of the card on his/her personal board
     * @throws RemoteException if the connection fails
     */
    void placeCard(ResourceCard card_chosen, Player p, int x, int y) throws RemoteException;

    /**
     * This method place a GOLDCARD in the commonboard.
     * @param card_chosen the card to place
     * @param p the player that place the card
     * @param x the x coordinate of the card on his/her personal board
     * @param y the y coordinate of the card on his/her drawpersonal board
     * @throws RemoteException if the connection fails
     */
    void placeCard(GoldCard card_chosen, Player p, int x, int y) throws RemoteException;

    /**
     * Draw a card from the deck in commonBoard
     * @param player_nickname
     * @param index the index of the card to draw
     */
    void drawCard(String player_nickname, int index) throws RemoteException;

    /**
     * this method must be called every time a player finishes his/her turn,
     * i.e. whenever he/she has placed a card on his/her personal board and has also
     * drawn a new game card from the deck/table
     * @throws RuntimeException if the connection fails
     */
    void myTurnIsFinished() throws RuntimeException;

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
    Player getPlayerEntity(String nickname);

    /**
     * Set the @param p player ready to start
     * When all the players are ready to start, the game starts (game status changes to running)
     *
     * @param nickname the nickname of the player
     * @return true if the player is ready to start
     * @throws RemoteException if the connection fails
     */
    void setPlayerAsReadyToStart(String nickname) throws RemoteException;

    /**
     * Check if the game is ready to start
     * @return true if the game is ready to start, false else
     */
    boolean isTheGameReadyToStart() throws RemoteException;

    /**
     * This method starts the game
     * @throws IllegalStateException if the game is not ready to start
     * @throws RemoteException if the connection fails
     */
    void startGame() throws IllegalStateException;

    /**
     * This method is used by the server to add a ping every x second in order to check for disconnections.
     * @param nickname
     * @param me
     * @throws RemoteException
     */

    void addPing(String nickname, GameListener me) throws RemoteException;

    /**
     * This method disconnect a player and remove him from the GameListener list{@link GameListener}
     * @param p the player to disconnect
     * @param listOfClient the GameListener of the player {@link GameListener}
     * @throws RemoteException if the connection fails
     */
    void disconnectPlayer( Player p, GameListener listOfClient) throws RemoteException, GameEndedException;

    /**
     * Add a message to the chat list
     * @param mess the message to send {@link Message}
     * @throws RemoteException if the connection fails
     */
    void sendMessage(Message mess) throws RemoteException;

    /**
     * This method return the id of the game
     * @return the id of the game
     */
    int getGameId();

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
}