package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.Listener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.demo.networking.PrintAsync.printAsync;

/**
 * RMIServer Class<br>
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game<br>
 * by the RMI Network protocol
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    /**
     * MainController of all the games
     */
    private final MainControllerInterface mainController;

    /**
     * RMIServer object
     */
    private static RMIServer serverObject = null;

    /**
     * Registry associated with the RMI Server
     */
    private static Registry registry = null;

    /**
     * Create a RMI Server
     * @return the instance of the server
     */
    public static RMIServer bind() {
        try {
            serverObject = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(DefaultValues.Default_port_RMI);
            getRegistry().rebind(DefaultValues.Default_servername_RMI, serverObject);
            printAsync("Server RMI ready");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return getInstance();
    }


    /**
     * Singleton pattern
     * @return the instance of the RMI Server
     */
    public synchronized static RMIServer getInstance() {
        if (serverObject == null) {
            try {
                serverObject = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    /**
     * @return the registry associated with the RMI Server
     * @throws RemoteException
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Constructor that creates an RMI Server
     * @throws RemoteException if the connection fails
     */
    public RMIServer() throws RemoteException {
        super(0);
        mainController = MainController.getControllerInstance();
    }

    /**
     * A player requested, through the network, to create a new game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the new created game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface createGame(Listener lis, String nick, int num_of_players)
            throws RemoteException {

        // Before every call, we need to recreate the stub, or java will just GC everything.
        GameControllerInterface game = serverObject.mainController.createGame(lis, nick, num_of_players);
        // The GameController and the Player have just been created. Hence, we need to set them as an Exportable Object

        try {
            UnicastRemoteObject.exportObject(game, 0);
        } catch (RemoteException e){
            // Already exported, due to another RMI Client running on the same machine
        }
        // ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
        printAsync("[RMI] " + nick + " has created a new game");
        return game;
    }

    /**
     * A player requested, through the network, to join a specific game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the specific game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException {

        // Return the GameController already existed => not necessary to re-Export Object
        GameControllerInterface ris = serverObject.mainController.joinGame(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
            // ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
            printAsync("[RMI] " + nick + " joined to specific game with id: " + idGame);
        }
        return ris;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(Listener lis, String nick) throws RemoteException {
        GameControllerInterface ris = serverObject.mainController.joinFirstAvailableGame(lis, nick);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
            printAsync("[RMI] " + nick + " joined in first available game");
        }
        return ris;
    }

    @Override
    public GameControllerInterface setAsReady(Listener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface ris = serverObject.mainController.setAsReady(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return ris;
    }

    @Override
    public GameControllerInterface placeStarterCard(Listener lis, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface ris = serverObject.mainController.placeStarterCard(lis, nick, o, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return ris;
    }

    @Override
    public GameControllerInterface chooseCard(Listener lis, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface ris = serverObject.mainController.chooseCard(lis, nick, cardIndex, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return ris;
    }

    @Override
    public GameControllerInterface placeCard(Listener lis, String nick, int where_to_place_x, int where_to_place_y, Orientation orientation, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface ris = serverObject.mainController.placeCard(lis, nick, where_to_place_x, where_to_place_y, orientation, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return ris;
    }

    @Override
    public GameControllerInterface drawCard(Listener lis, String nick, int index, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface ris = serverObject.mainController.drawCard(lis, nick, index, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return ris;
    }

    @Override
    public GameControllerInterface sendMessage(Listener lis, String nick, Message message, int idGame) throws RemoteException {
        GameControllerInterface ris = serverObject.mainController.sendMessage(lis, nick, message, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            } catch (RemoteException e){
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return ris;
    }

    /**
     * A player requested, through the network, to reconnect to a game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface reconnect(Listener lis, String nick, int idGame) throws RemoteException {

        GameControllerInterface ris = serverObject.mainController.reconnect(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            }
            // ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),DefaultValue.Default_port_RMI));
            // printAsync("[RMI] "+nick+" joined to specific game with id: "+idGame);
        }
        return ris;
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
    public GameControllerInterface leaveGame(Listener lis, String nick, int idGame) throws RemoteException {
        serverObject.mainController.leaveGame(lis,nick,idGame);
        return null;
    }

    @Override
    public GameControllerInterface getGameController(int idGame) throws RemoteException {
        return serverObject.mainController.getGameController(idGame);
    }

    /**
     * Close the RMI Server
     * Used only for testing purposes
     *
     * @return RMI Server
     */
    @Deprecated
    public static RMIServer unbind(){
        try {
            getRegistry().unbind(DefaultValues.Default_servername_RMI);
            UnicastRemoteObject.unexportObject(getRegistry(), true);
            printAsync("Server RMI correctly closed");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
        } catch (NotBoundException e) {
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getInstance();
    }


}
