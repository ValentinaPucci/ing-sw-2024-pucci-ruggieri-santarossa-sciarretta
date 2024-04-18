package it.polimi.demo.networking.rmi.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IT IS THE STUB  --> COMMON CLIENT ACTIONS
 *
 * It contains methods of MainController, that clients want to call from server.
 */


public interface VirtualServer extends Remote {
     void login(MainControllerInterface cc) throws RemoteException;
     void send (String message) throws RemoteException;
}
