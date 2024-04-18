package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.VirtualServer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import it.polimi.demo.model.DefaultValues;
import java.util.Map;
import java.util.Scanner;


/**
 * RMI server implementation that provides remote access to clients.
 * Implements the VirtualServer interface to handle client connections and message transmission.
 */
public class RmiServer extends UnicastRemoteObject implements VirtualServer {
    /**
     * RMIServer object
     */


    final MainController controller;
    /** List of connected client interfaces */
    final List<MainControllerInterface> virtualClients;

    /**
     * Constructor for the RMI server.
     * Initializes the list of virtualClients.
     * @throws RemoteException if an RMI error occurs
     */
    protected RmiServer(MainController controller) throws RemoteException {
        this.virtualClients = new ArrayList<>();
        this.controller = controller;
    }

    /**
     * Logs in a client by adding its MainControllerInterface to the list of virtualClients.
     * @param vc the MainControllerInterface of the client to be logged in
     */
    public void login(MainControllerInterface vc) throws RemoteException {
        System.err.println("new client connected");
        synchronized (virtualClients) {
            this.virtualClients.add(vc);
        }
    }

    /**
     * Sends a message to all connected clients.
     * @param message the message to be sent
     * @throws RemoteException if an RMI error occurs during message transmission
     */
    public void send(String message) throws RemoteException {
        System.out.println("Server received: " + message);
        for (MainControllerInterface vc : virtualClients) {
            vc.receive(message);
        }
    }

    @Override
    public void addPlayerToGame(int gameID, String username) throws RemoteException {

    }

    @Override
    public void create(int numberOfPlayers, String username) throws RemoteException {

    }

    @Override
    public void getGamesList() throws RemoteException {

    }

    @Override
    public void performTurn() throws RemoteException {

    }

    /**
     * Main method to start the RMI server.
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            new RmiServer(new MainController()).startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the RMI server by binding its stub in the registry.
     * @throws RemoteException if an RMI error occurs during server startup
     */
    private void startServer() throws RemoteException {
        // Create or get the registry
        Registry registry = LocateRegistry.createRegistry(DefaultValues.PORT);
        try {
            // Bind the server object in the registry
            registry.bind("ServerTest", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server ready");
    }


}