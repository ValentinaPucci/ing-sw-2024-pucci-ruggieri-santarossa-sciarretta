package it.polimi.demo.networking.rmi.remoteInterfaces;

import it.polimi.demo.networking.rmi.RmiClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IT IS THE STUB  --> COMMON CLIENT ACTIONS
 * It is the remote interface for the virtual server in the RMI system.
 * It contains methods of MainController, that clients want to call from server.
 */

public interface VirtualServer extends Remote {

     /**
      * Logs in a client to the server.
      * @param cc the MainControllerInterface of the client to be logged in
      * @throws RemoteException if an RMI error occurs during the login process
      */
     void login(MainControllerInterface cc) throws RemoteException;

     /**
      * Sends a message to all connected clients.
      * @param message the message to be sent
      * @throws RemoteException if an RMI error occurs during message transmission
      */
     void send(String message) throws RemoteException;


     /**
      * This method is called by the client to join an already existing game.
      * @param gameID the ID of the game to join
      * @param username the username of the player
      */
     void addPlayerToGame(int gameID, String username) throws RemoteException;

     /**
      * This method is called by the client to create a new game.
      * @param numberOfPlayers the number of players in the game
      * @param username the username of the player
      *
      */
     void create(int numberOfPlayers, String username) throws RemoteException;

     /**
      * This method is called by the client to get the list of games.
      * The client will be notified of the list of games.
      */
     void getGamesList() throws RemoteException;

     /**
      * This method is called by the client to perform a turn.
      */
     void performTurn() throws RemoteException;

}
