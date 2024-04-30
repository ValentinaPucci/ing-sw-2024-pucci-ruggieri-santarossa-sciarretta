package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.GameListener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.networking.ControllerInterfaces.GameControllerInterface;
import it.polimi.demo.networking.ControllerInterfaces.MainControllerInterface;
import it.polimi.demo.networking.GenericServer;

import static org.fusesource.jansi.Ansi.ansi;
import static it.polimi.demo.networking.PrintAsync.printAsync;

/**
 * RMI server implementation that provides remote access to clients.
 * Implements the VirtualServer interface to handle client connections and message transmission.
 *
 * Remark: This class couples with the MainControllerInterface.
 */
public class RmiServer extends GenericServer implements MainControllerInterface {

    private static Registry registryInstance = null;

    public RmiServer() throws RemoteException {
        super();
    }

    /**
     * Returns the registry instance.
     * @return the registry instance
     * @throws RemoteException if there is an RMI-related exception
     */
    public synchronized static Registry getRegistryInstance() throws RemoteException {
        if (registryInstance == null) {
            registryInstance = LocateRegistry.createRegistry(DefaultValues.Default_port_RMI);
        }
        return registryInstance;
    }

    /**
     * This method is called by the main server (app server) to start the RMI server.
     * @return the RMI server instance
     * @throws RemoteException if there is an RMI-related exception
     */
    public static RmiServer startServer() throws RemoteException {
        try {
            registryInstance = LocateRegistry.createRegistry(DefaultValues.Default_port_RMI);
            getRegistryInstance().rebind(DefaultValues.RMI_ServerName, getServerInstance());
            printAsync("Server RMI ready");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return (RmiServer) getServerInstance();
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
        GameControllerInterface gameController = mainController.createGame(lis, nick, num_of_players);
        exportObject(gameController);
        return gameController;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        GameControllerInterface ris = mainController.joinFirstAvailableGame(lis, nick);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e) {
                // Already exported, due to another RMI Client running on the same machine
            }
            printAsync("[RMI] " + nick + " joined in first available game");
        }
        return ris;
    }

    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface ris = mainController.joinGame(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e) {
                // Already exported, due to another RMI Client running on the same machine
            }
            printAsync("[RMI] " + nick + " joined to specific game with id: " + idGame);
        }
        return ris;
    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface ris = mainController.reconnect(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e) {
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return ris;
    }

    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        mainController.leaveGame(lis,nick,idGame);
        return null;
    }
}