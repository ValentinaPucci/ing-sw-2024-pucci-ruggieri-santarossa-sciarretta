package it.polimi.demo.networking;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.networking.ControllerInterfaces.MainControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GenericServer extends UnicastRemoteObject {

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    protected final MainControllerInterface mainController;

    protected VirtualClient client;

    //TODO: CAPIRE BENE COME USARE I LISTENERS
    private GameListener lis;
    protected final ListenersHandler listeners_handler;
    protected static GenericServer serverInstance = null;
    protected static Registry registryInstance = null;


    public GenericServer() throws RemoteException {
        super(0);
        mainController = MainController.getControllerInstance();
        listeners_handler = new ListenersHandler();
    }

    public void register(VirtualClient client) throws RemoteException {
        this.client = client;
        System.out.println("A client is registering to the server...");
        //il listener è aggiunto nel main controller
        //TODO: executor.submit(this.pingpongThread);
    }

    public void createGame(int numberOfPlayers, String nickname) throws RemoteException {
        mainController.createGame(lis, nickname, numberOfPlayers);
    }
}
