package it.polimi.demo.network.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.network.utils.StaticPrinter;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI server class that implements the {@link MainControllerInterface}.
 * This class handles the creation, joining, and management of games via RMI.
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    /**
     * serial number for serialization
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Reference to the main controller
     */
    private final MainControllerInterface primary_controller;

    /**
     * Constructor that initializes the RMIServer.
     *
     * @throws RemoteException if the server cannot be created
     */
    public RMIServer() throws RemoteException {
        super(0);
        primary_controller = MainController.getControllerInstance();
        initialize();
    }

    /**
     * Initializes the RMI server by creating the registry and binding the server instance.
     */
    public void initialize() {
        try {
            Registry reg = LocateRegistry.createRegistry(Constants.RMI_port);
            reg.rebind(Constants.RMI_server_name, this);
            StaticPrinter.staticPrinter("RMI Server ready");
        } catch (RemoteException ex) {
            logError(ex);
        }
    }

    /**
     * Logs errors that occur during server operations.
     *
     * @param ex the exception that was thrown
     */
    private static void logError(Exception ex) {
        System.err.printf("[ERROR] %s: \n\tRMI server exception: %s%n", "RMI SERVER STARTUP", ex);
    }

    /**
     * Registers a game with the RMI server.
     *
     * @param gameController the game controller to be registered
     * @return the game controller
     * @throws RemoteException if the game controller cannot be registered
     */
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

    /**
     * Creates a new game and returns the game controller.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param num_of_players the number of players in the game
     * @return the game controller
     * @throws RemoteException if the game cannot be created
     */
    @Override
    public GameControllerInterface createGame(Listener lis, String nick, int num_of_players) throws RemoteException {
        GameControllerInterface game = primary_controller.createGame(lis, nick, num_of_players);
        StaticPrinter.staticPrinter("[RMI] " + nick + " has created a new game");
        return registerGame(game);
    }

    /**
     * Joins a game with a specific ID and returns the game controller.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param idGame the ID of the game to join
     * @return the game controller
     * @throws RemoteException if the game cannot be joined
     */
    @Override
    public GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface game = primary_controller.joinGame(lis, nick, idGame);
        StaticPrinter.staticPrinter("[RMI] " + nick + " joined specific game with id: " + idGame);
        return registerGame(game);
    }

    /**
     * Joins a random game and returns the game controller.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @return the game controller
     * @throws RemoteException if the game cannot be joined
     */
    @Override
    public GameControllerInterface joinRandomly(Listener lis, String nick) throws RemoteException {
        GameControllerInterface game = primary_controller.joinRandomly(lis, nick);
        StaticPrinter.staticPrinter("[RMI] " + nick + " joined a random game");
        return registerGame(game);
    }

    /**
     * Sets the player as ready for the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param idGame the ID of the game
     * @throws RemoteException if the player cannot be set as ready
     */
    @Override
    public void setAsReady(Listener lis, String nick, int idGame) throws RemoteException {
        primary_controller.setAsReady(lis, nick, idGame);
    }

    /**
     * Places the starter card for the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param o the orientation of the card
     * @param idGame the ID of the game
     * @throws RemoteException if the card cannot be placed
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void placeStarterCard(Listener lis, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException {
        primary_controller.placeStarterCard(lis, nick, o, idGame);
    }

    /**
     * Chooses a card for the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param cardIndex the index of the card to choose
     * @param idGame the ID of the game
     * @throws RemoteException if the card cannot be chosen
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void chooseCard(Listener lis, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException {
        primary_controller.chooseCard(lis, nick, cardIndex, idGame);
    }

    /**
     * Places a card in the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param where_to_place_x the x coordinate to place the card
     * @param where_to_place_y the y coordinate to place the card
     * @param orientation the orientation of the card
     * @param idGame the ID of the game
     * @throws RemoteException if the card cannot be placed
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void placeCard(Listener lis, String nick, int where_to_place_x, int where_to_place_y, Orientation orientation, int idGame) throws RemoteException, GameEndedException {
        primary_controller.placeCard(lis, nick, where_to_place_x, where_to_place_y, orientation, idGame);
    }

    /**
     * Draws a card for the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param index the index of the card to draw
     * @param idGame the ID of the game
     * @throws RemoteException if the card cannot be drawn
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void drawCard(Listener lis, String nick, int index, int idGame) throws RemoteException, GameEndedException {
        primary_controller.drawCard(lis, nick, index, idGame);
    }

    /**
     * Sends a message in the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param message the message to send
     * @param idGame the ID of the game
     * @throws RemoteException if the message cannot be sent
     */
    @Override
    public void sendMessage(Listener lis, String nick, Message message, int idGame) throws RemoteException {
        primary_controller.sendMessage(lis, nick, message, idGame);
    }

    /**
     * Shows the personal board of the player in the game.
     * @param lis the listener for game events
     * @param nickname the nickname of the player
     * @param playerIndex the index of the player
     * @param idGame the ID of the game
     * @throws RemoteException if the personal board cannot be shown
     */
    @Override
    public void showOthersPersonalBoard(Listener lis, String nickname, int playerIndex, int idGame) throws RemoteException {
        primary_controller.showOthersPersonalBoard(lis, nickname, playerIndex, idGame);
    }

    /**
     * Adds a ping to the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param idGame the ID of the game
     * @throws RemoteException if the ping cannot be added
     */
    @Override
    public void addPing(Listener lis, String nick, int idGame) throws RemoteException {
        primary_controller.addPing(lis, nick, idGame);
    }

    /**
     * Allows a player to leave the game.
     *
     * @param lis the listener for game events
     * @param nick the nickname of the player
     * @param idGame the ID of the game
     * @throws RemoteException if the player cannot leave the game
     */
    @Override
    public void leaveGame(Listener lis, String nick, int idGame) throws RemoteException {
        primary_controller.leaveGame(lis, nick, idGame);
    }

    /**
     * Gets the game controller for a specific game.
     *
     * @param idGame the ID of the game
     * @return the game controller
     * @throws RemoteException if the game controller cannot be retrieved
     */
    @Override
    public GameControllerInterface getGameController(int idGame) throws RemoteException {
        return primary_controller.getGameController(idGame);
    }
}

