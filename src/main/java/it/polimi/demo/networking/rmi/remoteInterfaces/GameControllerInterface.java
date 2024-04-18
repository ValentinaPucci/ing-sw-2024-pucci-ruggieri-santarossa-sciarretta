package it.polimi.demo.networking.rmi.remoteInterfaces;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.cards.Card;
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
     * This method is used to check if the player is ready to start
     * @param p the nickname of the player
     * @return true if the player is ready to start
     * @throws RemoteException if the connection fails
     */
    boolean playerIsReadyToStart(String p) throws RemoteException;

    Card drawCard(int index) throws RemoteException;

    /**
     * This method place a card in the commonboard.
     * @param card_chosen
     * @param p
     * @param x
     * @param y
     * @throws RemoteException
     */
    void placeCard(ResourceCard card_chosen, Player p, int x, int y) throws RemoteException, GameEndedException;

    /**
     * This method checks if it's the turn of the player
     * @param nick the nickname of the player
     * @return true if it's the turn of the player
     * @throws RemoteException if the connection fails
     */
    boolean isThisMyTurn(String nick) throws RemoteException;


    /**
     * This method disconnect a player and remove him from the GameListener list{@link GameListener}
     * @param nick the nickname of the player
     * @param listOfClient the GameListener of the player {@link GameListener}
     * @throws RemoteException if the connection fails
     */
    void disconnectPlayer(String nick, GameListener listOfClient) throws RemoteException;


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
    void sentMessage(Message msg) throws RemoteException;

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
    int getNumOnlinePlayers() throws RemoteException;

    /**
     * This method remove a player from the GameListener list {@link GameListener} and from the game
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @throws RemoteException if the connection fails
     */
    void leave(GameListener lis, String nick) throws RemoteException;
}