package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.networking.StaticPrinter;
import it.polimi.demo.observer.Listener;
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

public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private static final long serialVersionUID = 1L;

    private final MainControllerInterface mainController = MainController.getControllerInstance();

    private static RMIServer serverObject;

    private static Registry registry;

    private RMIServer() throws RemoteException {
        super(0);
    }

    private static class SingletonHelper {
        private static final RMIServer INSTANCE;

        static {
            try {
                INSTANCE = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static RMIServer getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public static RMIServer bind() {
        try {
            serverObject = getInstance();
            registry = LocateRegistry.createRegistry(DefaultValues.Default_port_RMI);
            registry.rebind(DefaultValues.Default_servername_RMI, serverObject);
            StaticPrinter.staticPrinter("Server RMI ready");
        } catch (RemoteException e) {
            handleError("STARTING RMI SERVER", e);
        }
        return getInstance();
    }

    private static void handleError(String s, Exception e) {
        System.err.printf("[ERROR] %s: \n\tServer RMI exception: %s%n", s, e);
    }

    public GameControllerInterface exportGame(GameControllerInterface game) throws RemoteException {
        if (game == null) return null;
        // Check if the object is already exported before exporting it
        try {
            UnicastRemoteObject.exportObject(game, 0);
        } catch (RemoteException e) {
            if (e.getMessage().contains("object already exported")) {
                // If already exported, return the same object without exporting again
                return game;
            } else {
                throw e;
            }
        }
        return game;
    }

    @Override
    public GameControllerInterface createGame(Listener lis, String nick, int num_of_players) throws RemoteException {
        GameControllerInterface game = mainController.createGame(lis, nick, num_of_players);
        StaticPrinter.staticPrinter("[RMI] " + nick + " has created a new game");
        return exportGame(game);
    }

    @Override
    public GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface game = mainController.joinGame(lis, nick, idGame);
        StaticPrinter.staticPrinter("[RMI] " + nick + " joined specific game with id: " + idGame);
        return exportGame(game);
    }

    @Override
    public GameControllerInterface joinRandomly(Listener lis, String nick) throws RemoteException {
        GameControllerInterface game = mainController.joinRandomly(lis, nick);
        StaticPrinter.staticPrinter("[RMI] " + nick + " joined first available game");
        return exportGame(game);
    }

    @Override
    public void setAsReady(Listener lis, String nick, int idGame) throws RemoteException {
        mainController.setAsReady(lis, nick, idGame);
    }

    @Override
    public void placeStarterCard(Listener lis, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException {
        mainController.placeStarterCard(lis, nick, o, idGame);
    }

    @Override
    public void chooseCard(Listener lis, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException {
        mainController.chooseCard(lis, nick, cardIndex, idGame);;
    }

    @Override
    public void placeCard(Listener lis, String nick, int where_to_place_x, int where_to_place_y, Orientation orientation, int idGame) throws RemoteException, GameEndedException {
       mainController.placeCard(lis, nick, where_to_place_x, where_to_place_y, orientation, idGame);
    }

    @Override
    public void drawCard(Listener lis, String nick, int index, int idGame) throws RemoteException, GameEndedException {
        mainController.drawCard(lis, nick, index, idGame);
    }

    @Override
    public void sendMessage(Listener lis, String nick, Message message, int idGame) throws RemoteException {
        mainController.sendMessage(lis, nick, message, idGame);
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
