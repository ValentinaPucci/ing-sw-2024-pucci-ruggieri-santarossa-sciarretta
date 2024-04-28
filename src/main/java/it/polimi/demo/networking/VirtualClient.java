package it.polimi.demo.networking;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.chat.Message;

/**
 * IT IS THE STUB  --> COMMON CLIENT ACTIONS
 * It is the remote interface for the virtual server in the RMI system.
 * It contains methods of MainController, that clients want to call from server.
 *
 * Remark: This interface couples with the MainControllerInterface and GameControllerInterface interfaces,
 * but it represents the client's point of view: in fact, this interface is the dual of the previously mentioned
 * interfaces and is implemented by the client.
 */
public interface VirtualClient extends Remote {

     //--------------------------------------------MainControllerInterface---------------------------------------------------------

     /**
      * Creates a new game
      *
      * @param nick      nickname of the player
      * @throws IOException if the connection is lost
      * @throws InterruptedException if the thread is interrupted
      * @throws NotBoundException if the server is not bound
      */
     void createGame(String nick, int num_players) throws IOException, InterruptedException, NotBoundException;

     /**
      * Joins the first game found in the list of games
      *
      * @param nick nickname of the player
      * @throws IOException if the connection is lost
      * @throws InterruptedException if the thread is interrupted
      * @throws NotBoundException if the server is not bound
      */
     void joinFirstAvailable(String nick) throws IOException, InterruptedException, NotBoundException;

     /**
      * Adds the player to the game
      *
      * @param nick nickname of the player
      * @param idGame id of the game
      * @throws IOException if the connection is lost
      * @throws InterruptedException if the thread is interrupted
      * @throws NotBoundException if the server is not bound
      */
     void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

     /**
      * Reconnect the player to the game
      *
      * @param nick nickname of the player
      * @param idGame id of the game
      * @throws IOException if the connection is lost
      * @throws InterruptedException if the thread is interrupted
      * @throws NotBoundException if the server is not bound
      */
     void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

     /**
      * Leaves the game
      *
      * @param nick nickname of the player
      * @param idGame id of the game
      * @throws IOException if the connection is lost
      * @throws NotBoundException if the server is not bound
      */
     void leave(String nick, int idGame) throws IOException, NotBoundException;

     //------------------------------------------GameControllerInterface------------------------------------------------------------

     /**
      * Sets the invoker as ready
      *
      * @throws IOException if the connection is lost
      */
     void setAsReady() throws IOException;

     /**
      * Checks if it's the invoker's turn
      *
      * @return true if it's the invoker's turn, false otherwise
      * @throws RemoteException if the connection is lost
      */
     boolean isMyTurn() throws RemoteException;

     /**
      * draws a card from the deck or from the table based on the index given
      *
      * @param player_nickname The nickname of the player who wants to draw a card
      *
      * @param index The index indicating which card to draw:
      *                    1: Resource Deck
      *                    2: First Resource Card on the table
      *                    3: Second Resource Card on the table
      *                    4: Gold Deck
      *                    5: First Gold Card on the table
      *                    6: Second Gold Card on the table
      *
      * @throws IOException if the connection is lost
      */
     void drawCard(String player_nickname, int index) throws IOException;

     /**
      * place a resource card on the personal board of the player
      * @param card_chosen the card to place
      * @param x the x coordinate on the personal board
      * @param y the y coordinate on the personal board
      * @throws IOException if the connection is lost
      */
     void placeCard(ResourceCard card_chosen, int x, int y) throws IOException;

     /**
      * place a gold card on the personal board of the player
      * @param card_chosen the card to place
      * @param x the x coordinate on the personal board
      * @param y the y coordinate on the personal board
      * @throws IOException if the connection is lost
      */
     void placeCard(GoldCard card_chosen, int x, int y) throws IOException;

     /**
      * Sends a message in chat
      *
      * @param msg message
      * @throws RemoteException if the connection is lost
      */
     void sendMessage(Message msg) throws RemoteException;

     /**
      * Pings the server
      *
      * @throws RemoteException if the connection is lost
      */
     void addPing() throws RemoteException;

}
