package it.polimi.demo.networking.rmi.remoteInterfaces;

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
}
