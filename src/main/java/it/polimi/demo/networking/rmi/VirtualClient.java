package it.polimi.demo.networking.rmi;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.demo.model.Player;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.exceptions.*;

/**
 * IT IS THE STUB  --> COMMON CLIENT ACTIONS
 * It is the remote interface for the virtual server in the RMI system.
 * It contains methods of MainController, that clients want to call from server.
 */

public interface VirtualClient extends Remote {

     /**
      * Creates a new game
      *
      * @param nick
      * @throws IOException
      * @throws InterruptedException
      * @throws NotBoundException
      */
     void createGame(String nick, int num_players) throws IOException, InterruptedException, NotBoundException;

     /**
      * Joins the first game found in the list of games
      *
      * @param nick
      * @throws IOException
      * @throws InterruptedException
      * @throws NotBoundException
      */
     void joinFirstAvailable(String nick) throws IOException, InterruptedException, NotBoundException;

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

     //------------------------------------------------------------------------------------------------------

     /**
      * Sets the invoker as ready
      *
      * @throws IOException
      */
     void setAsReady() throws IOException;

     /**
      * Checks if it's the invoker's turn
      *
      * @return
      * @throws RemoteException
      */
     boolean isMyTurn() throws RemoteException;


     void drawCard(String player_nickname, int index) throws IOException;


     void placeCard(ResourceCard card_chosen, int x, int y) throws IOException;

     void placeCard(GoldCard card_chosen, int x, int y) throws IOException;

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
     void addPing() throws RemoteException;

}
