package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.GameListener;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import it.polimi.demo.model.DefaultValues;

import java.util.Scanner;

import java.io.IOException;
import java.util.Arrays;

import static org.fusesource.jansi.Ansi.ansi;
import static it.polimi.demo.networking.PrintAsync.printAsync;


/**
 * RMI server implementation that provides remote access to clients.
 * Implements the VirtualServer interface to handle client connections and message transmission.
 */
public class RmiServer extends UnicastRemoteObject implements MainControllerInterface {

    /**
     * MainController of all the games
     */
    private final MainControllerInterface mainController;

    /**
     * RMIServer object
     */
    private static RmiServer serverObject = null;

    /**
     * Registry associated with the RMI Server
     */
    private static Registry registry = null;

    /**
     * Constructor that creates a RMI Server
     * @throws RemoteException
     */
    public RmiServer() throws RemoteException {
        super(0);
        mainController = MainController.getInstance();
    }

    /**
     * Create a RMI Server
     * @return the instance of the server
     */
    public static RmiServer bind() {
        try {
            // Creazione dell'oggetto del server RMI
            serverObject = new RmiServer();
            // Creazione del registro RMI sulla porta predefinita
            registry = LocateRegistry.createRegistry(DefaultValues.Default_port_RMI);
            // Associazione dell'oggetto del server al registro
            registry.rebind(DefaultValues.RMI_ServerName, serverObject);
            // Messaggio di conferma che il server RMI è pronto
            printAsync("Server RMI ready");
        } catch (RemoteException e) {
            // Gestione delle eccezioni di RemoteException
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        // Ritorno dell'oggetto del server creato
        return getInstance();
    }
    /**
     * Main entry point for the RMI client application.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ip = askForRemoteIp(scanner);

        if (ip.isEmpty()) {
            System.setProperty("java.rmi.server.hostname", DefaultValues.Remote_ip);
        } else {
            DefaultValues.Server_ip = ip;
            System.setProperty("java.rmi.server.hostname", ip);
        }
        RmiServer.bind();
    }

    /**
     * Prompts the user to enter the remote IP address.
     *
     * @param scanner The scanner object for user input
     * @return The entered remote IP address
     */
    private static String askForRemoteIp(Scanner scanner) {
        String input;
        do {
            printAsync("Insert remote IP (leave empty for localhost)");
            input = scanner.nextLine();
        } while (!input.isEmpty() && !isValidIP(input));
        return input;
    }

    private static boolean isValidIP(String input) {
        String[] parts = input.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }


    /**
     * Retrieves the singleton instance of the RMI server.
     *
     * @return the singleton instance of the RMI server
     */
    public synchronized static RmiServer getInstance() {
        if (serverObject == null) {
            try {
                serverObject = new RmiServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    /**
     * Retrieves the registry associated with the RMI server.
     *
     * @return the registry associated with the RMI server
     * @throws RemoteException if there is an RMI-related exception
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        if (registry == null) {
            registry = LocateRegistry.createRegistry(1099);
        }
        return registry;
    }

    /**
     * Creates a new game upon player request.
     *
     * @param lis  the listener for the game
     * @param nick the nickname of the player
     * @return the game controller interface for the new game
     * @throws RemoteException if there is an RMI-related exception
     */
    @Override
    public GameControllerInterface createGame(GameListener lis, String nick, int num_of_players) throws RemoteException {
        GameControllerInterface gameController = serverObject.mainController.createGame(lis, nick, num_of_players);
        exportObject(gameController);
        return gameController;
    }

    /**
     * Joins the first available game upon player request.
     *
     * @param lis  the listener for the game
     * @param nick the nickname of the player
     * @return the game controller interface for the joined game
     * @throws RemoteException if there is an RMI-related exception
     */
    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        GameControllerInterface gameController = serverObject.mainController.joinFirstAvailableGame(lis, nick);
        if (gameController != null) {
            exportObject(gameController);
        }
        return gameController;
    }

    /**
     * Joins a specific game upon player request.
     *
     * @param lis    the listener for the game
     * @param nick   the nickname of the player
     * @param idGame the ID of the game to join
     * @return the game controller interface for the joined game
     * @throws RemoteException if there is an RMI-related exception
     */
    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface gameController = serverObject.mainController.joinGame(lis, nick, idGame);
        if (gameController != null) {
            exportObject(gameController);
        }
        return gameController;
    }

    /**
     * Reconnects a player to a game upon request.
     *
     * @param lis    the listener for the game
     * @param nick   the nickname of the player
     * @param idGame the ID of the game to reconnect to
     * @return the game controller interface for the reconnected game
     * @throws RemoteException if there is an RMI-related exception
     */
    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface gameController = serverObject.mainController.reconnect(lis, nick, idGame);
        if (gameController != null) {
            exportObject(gameController);
        }
        return gameController;
    }

    /**
     * Exports the provided RMI object.
     *
     * @param obj the RMI object to export
     * @throws RemoteException if there is an RMI-related exception
     */
    private void exportObject(GameControllerInterface obj) throws RemoteException {
        try {
            UnicastRemoteObject.exportObject(obj, 0);
        } catch (RemoteException e) {
            // Already exported, due to another RMI Client running on the same machine
        }
    }

    /**
     * A player requested, through the network, to leave a game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @param idGame of the game to leave
     * @return GameControllerInterface of the game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        serverObject.mainController.leaveGame(lis,nick,idGame);
        return null;
    }

}