package it.polimi.demo.network.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.network.StaticPrinter;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI server class
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    /**
     * serial number
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * the registry
     */
    private static Registry reg;

    /**
     * reference to the main controller
     */
    private final MainControllerInterface primary_controller;

    /**
     *
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException {
        super(0);
        primary_controller = MainController.getControllerInstance();
        initialize();
    }

    public void initialize() {
        try {
            reg = LocateRegistry.createRegistry(Constants.RMI_port);
            reg.rebind(Constants.RMI_server_name, this);
            StaticPrinter.staticPrinter("RMI Server ready");
        } catch (RemoteException ex) {}
    }

    public GameControllerInterface registerGame(GameControllerInterface gameController) throws RemoteException {
        if (gameController == null)
            return null;
        // Ensure the object is not exported again if already exported
        try {
            UnicastRemoteObject.exportObject(gameController, 0);
        } catch (RemoteException ex) {
            if (ex.getMessage().contains("object already exported")) {
                // If already exported, return the existing object
                return gameController;
            } else {
                throw ex;
            }
        }
        return gameController;
    }


    @Override
    public GameControllerInterface createGame(Listener lis, String nick, int num_of_players) throws RemoteException {
        GameControllerInterface game = primary_controller.createGame(lis, nick, num_of_players);
        StaticPrinter.staticPrinter("[RMI] " + nick + " has created a new game");
        return registerGame(game);
    }

    @Override
    public GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface game = primary_controller.joinGame(lis, nick, idGame);
        StaticPrinter.staticPrinter("[RMI] " + nick + " joined specific game with id: " + idGame);
        return registerGame(game);
    }

    @Override
    public GameControllerInterface joinRandomly(Listener lis, String nick) throws RemoteException {
        GameControllerInterface game = primary_controller.joinRandomly(lis, nick);
        StaticPrinter.staticPrinter("[RMI] " + nick + " joined a random game");
        return registerGame(game);
    }

    @Override
    public void setAsReady(Listener lis, String nick, int idGame) throws RemoteException {
        primary_controller.setAsReady(lis, nick, idGame);
    }

    @Override
    public void placeStarterCard(Listener lis, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException {
        primary_controller.placeStarterCard(lis, nick, o, idGame);
    }

    @Override
    public void chooseCard(Listener lis, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException {
        primary_controller.chooseCard(lis, nick, cardIndex, idGame);;
    }

    @Override
    public void placeCard(Listener lis, String nick, int where_to_place_x, int where_to_place_y, Orientation orientation, int idGame) throws RemoteException, GameEndedException {
       primary_controller.placeCard(lis, nick, where_to_place_x, where_to_place_y, orientation, idGame);
    }

    @Override
    public void drawCard(Listener lis, String nick, int index, int idGame) throws RemoteException, GameEndedException {
        primary_controller.drawCard(lis, nick, index, idGame);
    }

    @Override
    public void sendMessage(Listener lis, String nick, Message message, int idGame) throws RemoteException {
        primary_controller.sendMessage(lis, nick, message, idGame);
    }

    @Override
    public void addPing(Listener lis, String nick, int idGame) throws RemoteException {
        primary_controller.addPing(lis, nick, idGame);
    }

    @Override
    public void leaveGame(Listener lis, String nick, int idGame) throws RemoteException {
        primary_controller.leaveGame(lis, nick, idGame);
    }

    @Override
    public GameControllerInterface getGameController(int idGame) throws RemoteException {
        return primary_controller.getGameController(idGame);
    }
}
