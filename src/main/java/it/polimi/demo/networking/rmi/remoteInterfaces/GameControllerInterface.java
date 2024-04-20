package it.polimi.demo.networking.rmi.remoteInterfaces;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains all the action a player can do in a single game */
public interface GameControllerInterface extends Remote {

    /**
     * This method place a card in the commonboard.
     * @param card_chosen the card to place
     * @param p the player that place the card
     * @param x the x coordinate of the card on his/her personal board
     * @param y the y coordinate of the card on his/her personal board
     * @throws RemoteException if the connection fails
     */
    void placeCard(ResourceCard card_chosen, Player p, int x, int y) throws RemoteException;

    /**
     * This method place a card in the commonboard.
     * @param card_chosen the card to place
     * @param p the player that place the card
     * @param x the x coordinate of the card on his/her personal board
     * @param y the y coordinate of the card on his/her personal board
     * @throws RemoteException if the connection fails
     */
    void placeCard(GoldCard card_chosen, Player p, int x, int y) throws RemoteException;

    /**
     * This method is used to draw a resource card from the deck
     * @param index the index of the card to draw
     * @return the card drawn
     * @throws RemoteException if the connection fails
     */
    ResourceCard drawResourceCard(int index) throws RemoteException;

    /**
     * This method is used to draw a gold card from the deck
     * @param index the index of the card to draw
     * @return the card drawn
     * @throws RemoteException if the connection fails
     */
    GoldCard drawGoldCard(int index) throws RemoteException;

    /**
     * this method must be called every time a player finishes his/her turn,
     * i.e. whenever he/she has placed a card on his/her personal board and has also
     * drawn a new game card from the deck/table
     * @throws RemoteException if the connection fails
     */
    void myTurnIsFinished() throws RemoteException;

    /**
     * This method checks if it's the turn of the player
     * @param nick the nickname of the player
     * @return true if it's the turn of the player
     * @throws RemoteException if the connection fails
     */
    boolean isThisMyTurn(String nick) throws RemoteException;

    /**
     * This method is used to check if the player is ready to start
     * @param p the nickname of the player
     * @return true if the player is ready to start
     * @throws RemoteException if the connection fails
     */
    void setPlayerAsReadyToStart(String p) throws RemoteException;

    /**
     * This method is used to check if the game is ready to start, i.e.
     * if there are enough players to start the game
     * @return true if the game is ready to start
     * @throws RemoteException if the connection fails
     */
    boolean isTheGameReadyToStart() throws RemoteException;

    /**
     * This method starts the game
     * @throws IllegalStateException if the game is not ready to start
     * @throws RemoteException if the connection fails
     */
    void startGame() throws IllegalStateException, RemoteException;

    /**
     * This method disconnect a player and remove him from the GameListener list{@link GameListener}
     * @param nick the nickname of the player
     * @param listOfClient the GameListener of the player {@link GameListener}
     * @throws RemoteException if the connection fails
     */
    void disconnectPlayer(String nick, GameListener listOfClient) throws RemoteException, GameEndedException;

    /**
     * This method is used to check if the client is connected, every x seconds the server send a ping to the client
     * @param nick the nickname of the player
     * @param me the GameListener of the player {@link GameListener}
     * @throws RemoteException if the connection fails
     */
    void heartbeat(String nick, GameListener me) throws RemoteException;

    /**
     * This method sends a message
     * @param msg the message to send {@link Message}
     * @throws RemoteException if the connection fails
     */
    void sendMessage(Message msg) throws RemoteException;

    /**
     * This method return the id of the game
     * @return the id of the game
     * @throws RemoteException if the connection fails
     */
    int getGameId() throws RemoteException;

    /**
     * This method return the number of the online players
     * @return the number of the online players
     * @throws RemoteException if the connection fails
     */
    int getNumConnectedPlayers() throws RemoteException;

    /**
     * This method remove a player from the GameListener list {@link GameListener} and from the game
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @throws RemoteException if the connection fails
     */
    void leave(GameListener lis, String nick) throws RemoteException;
}