package it.polimi.demo.networking;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.networking.ControllerInterfaces.MainControllerInterface;
import it.polimi.demo.networking.rmi.RmiServer;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GenericServer extends UnicastRemoteObject {

    protected final MainControllerInterface mainController;
    protected static GenericServer serverInstance = null;
    protected static Registry registryInstance = null;
    protected final ListenersHandler listeners_handler;

    public GenericServer() throws RemoteException {
        super(0);
        mainController = MainController.getControllerInstance();
        listeners_handler = new ListenersHandler();
    }

    /**
     * Returns the server instance.
     * @return the server instance
     * @throws RemoteException if there is an RMI-related exception
     */
    public synchronized static GenericServer getServerInstance() throws RemoteException {
        if (serverInstance == null) {
            serverInstance = new GenericServer();
        }
        return serverInstance;
    }
}
