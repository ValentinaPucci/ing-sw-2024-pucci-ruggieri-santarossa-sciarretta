package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.VirtualServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * RMI client to communicate with the remote server using RMI.
 * Implements the MainControllerInterface to receive messages from the server.
 */
public class RmiClient implements MainControllerInterface, Serializable {

    private VirtualServer vs;

    /**
     * Starts the RMI client.
     * Connects to the remote server and initiates the input loop to send messages to the server.
     * @throws Exception if an error occurs while connecting to the server
     */
    private void startClient() throws Exception {
        // Code for connecting to the server
    }

    /**
     * Main method to start the RMI client.
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            new RmiClient().startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Input loop to send messages to the server.
     */
    void inputLoop() {
        // Code to read user input and send messages to the server
    }

    // Methods implemented from the MainControllerInterface

    /**
     * {@inheritDoc}
     */
    @Override
    public void receive(String message) throws RemoteException {
        // Implementation of the method to receive messages from the server
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

}
