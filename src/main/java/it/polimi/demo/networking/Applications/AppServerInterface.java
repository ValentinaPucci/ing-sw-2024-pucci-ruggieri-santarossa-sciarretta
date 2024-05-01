package it.polimi.demo.networking.Applications;

import it.polimi.demo.networking.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AppServerInterface extends Remote {
    /**
     * This method is called by the client to connect to the ServerImpl.
     * @return the ServerImpl instance that will be used by the client
     */
    Server connect() throws RemoteException;
}
