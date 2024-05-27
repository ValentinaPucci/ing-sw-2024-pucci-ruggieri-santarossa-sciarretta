package it.polimi.demo.view.flow;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface CommonClientActions {
    /**
     * Creates a new game
     *
     * @param nickname
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void createGame(String nickname, int num_of_players) throws IOException, InterruptedException, NotBoundException;

    /**
     * Adds the player to the game
     *
     * @param nick
     * @param idGame
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    void joinFirstAvailableGame(String nick) throws IOException, InterruptedException, NotBoundException;

    /**
     * Reconnect the player to the game
     *
     * @param nick
     * @param idGame
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    /**
     * Leaves the game
     *
     * @param nick
     * @param idGame
     * @throws IOException
     * @throws NotBoundException
     */
    void leave(String nick, int idGame) throws IOException, NotBoundException;


    void setAsReady() throws RemoteException, NotBoundException;

    /**
     * Checks if it's the invoker's turn
     *
     * @return
     * @throws RemoteException
     */
    boolean isMyTurn() throws RemoteException;

    /**
     * This method is called by the socket to get the list of players in the game.
     * @param orientation the orientation of the starter card
     */
    void placeStarterCard(Orientation orientation) throws IOException, GameEndedException, NotBoundException;

    /**
     * This method is called by the socket to choose a card.
     * @param which_card the index of the card the player wants to choose
     * @throws RemoteException if there is a network error
     */
    void chooseCard(int which_card) throws IOException, NotBoundException, GameEndedException;

    /**
     * This method is called by the socket to place a card
     * @param where_to_place_x the x coordinate of the cell where the player wants to place the card
     * @param where_to_place_y the y coordinate of the cell where the player wants to place the card
     * @throws RemoteException if there is a network error
     */
    void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws IOException, NotBoundException, GameEndedException;

    /**
     * This method is called by the socket to draw a card.
     * @param index the index of the card the player wants to draw
     * @throws RemoteException if there is a network error
     */
    void drawCard(int index) throws IOException, GameEndedException, NotBoundException;

    /**
     * Sends a message in chat
     *
     * @param msg message
     * @throws RemoteException
     */
    void sendMessage(Message msg) throws RemoteException;

    /**
     * Pings the server
     *
     * @throws RemoteException
     */
    void heartbeat() throws RemoteException;

}
