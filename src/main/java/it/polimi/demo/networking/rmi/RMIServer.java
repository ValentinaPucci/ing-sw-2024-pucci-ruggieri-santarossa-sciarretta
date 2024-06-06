package it.polimi.demo.networking.rmi;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.controller.MainController;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.demo.networking.PrintAsync.printAsync;

// todo: implement it differently
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private final MainControllerInterface mainController;

    private static RMIServer serverObject = null;

    private static Registry registry = null;

    public RMIServer() throws RemoteException {
        super(0);
        mainController = MainController.getControllerInstance();
    }

    public static RMIServer bind() {
        try {
            serverObject = new RMIServer();
            registry = LocateRegistry.createRegistry(DefaultValues.Default_port_RMI);
            registry.rebind(DefaultValues.Default_servername_RMI, serverObject);
            printAsync("Server RMI ready");
        } catch (RemoteException e) {
            handleError("STARTING RMI SERVER", e);
        }
        return getInstance();
    }

    public static synchronized RMIServer getInstance() {
        if (serverObject == null) {
            try {
                serverObject = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    private static void handleError(String context, Exception e) {
        e.printStackTrace();
        System.err.printf("[ERROR] %s: \n\tServer RMI exception: %s%n", context, e);
    }

    private GameControllerInterface exportGame(GameControllerInterface game) throws RemoteException {
        if (game != null) {
            try {
                UnicastRemoteObject.exportObject(game, 0);
            } catch (RemoteException ignored) {
                // Already exported, due to another RMI Client running on the same machine
            }
        }
        return game;
    }

    @Override
    public GameControllerInterface createGame(Listener lis, String nick, int num_of_players) throws RemoteException {
        GameControllerInterface game = mainController.createGame(lis, nick, num_of_players);
        printAsync("[RMI] " + nick + " has created a new game");
        return exportGame(game);
    }

    @Override
    public GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface game = mainController.joinGame(lis, nick, idGame);
        printAsync("[RMI] " + nick + " joined specific game with id: " + idGame);
        return exportGame(game);
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(Listener lis, String nick) throws RemoteException {
        GameControllerInterface game = mainController.joinFirstAvailableGame(lis, nick);
        printAsync("[RMI] " + nick + " joined first available game");
        return exportGame(game);
    }

    @Override
    public GameControllerInterface setAsReady(Listener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface game = mainController.setAsReady(lis, nick, idGame);
        return exportGame(game);
    }

    @Override
    public GameControllerInterface placeStarterCard(Listener lis, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface game = mainController.placeStarterCard(lis, nick, o, idGame);
        return exportGame(game);
    }

    @Override
    public GameControllerInterface chooseCard(Listener lis, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface game = mainController.chooseCard(lis, nick, cardIndex, idGame);
        return exportGame(game);
    }

    @Override
    public GameControllerInterface placeCard(Listener lis, String nick, int where_to_place_x, int where_to_place_y, Orientation orientation, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface game = mainController.placeCard(lis, nick, where_to_place_x, where_to_place_y, orientation, idGame);
        return exportGame(game);
    }

    @Override
    public GameControllerInterface drawCard(Listener lis, String nick, int index, int idGame) throws RemoteException, GameEndedException {
        GameControllerInterface game = mainController.drawCard(lis, nick, index, idGame);
        return exportGame(game);
    }

    @Override
    public GameControllerInterface sendMessage(Listener lis, String nick, Message message, int idGame) throws RemoteException {
        GameControllerInterface game = mainController.sendMessage(lis, nick, message, idGame);
        return exportGame(game);
    }

    @Override
    public void addPing(Listener lis, String nick, int idGame) throws RemoteException {
        mainController.addPing(lis, nick, idGame);
    }

    @Override
    public GameControllerInterface leaveGame(Listener lis, String nick, int idGame) throws RemoteException {
        mainController.leaveGame(lis, nick, idGame);
        return null;
    }

    @Override
    public GameControllerInterface getGameController(int idGame) throws RemoteException {
        return mainController.getGameController(idGame);
    }
}
