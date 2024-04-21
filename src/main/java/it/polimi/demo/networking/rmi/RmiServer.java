package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;

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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;
import static it.polimi.demo.networking.PrintAsync.printAsync;


/**
 * RMI server implementation that provides remote access to clients.
 * Implements the VirtualServer interface to handle client connections and message transmission.
 */
public class RmiServer extends UnicastRemoteObject implements MainControllerInterface {

    public RmiServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            // Creazione del registro RMI sulla porta 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Creazione dell'istanza del server RMI
            RmiServer server = new RmiServer();

            // Pubblicazione del server nel registro RMI
            registry.rebind("Server", server);

            System.out.println("Server RMI pronto.");
        } catch (Exception e) {
            System.err.println("Errore nel server RMI: " + e.toString());
            e.printStackTrace();
        }
    }

    // Metodi remoti che verrno chiamati dai client
    @Override
    public String sayHello() throws RemoteException {
        return "Ciao!";
    }


    @Override
    public void receive(String message) throws RemoteException {

    }

    @Override
    public GameControllerInterface createGame(GameListener lis, String nick, int num_players) throws RemoteException {
        System.out.println("Inizia una nuova partita con " + num_players + " giocatori.");
        printAsync("[RMI] " + nick + " has created a new game");
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

    @Override
    public void showError(String err) throws RemoteException {

    }
}