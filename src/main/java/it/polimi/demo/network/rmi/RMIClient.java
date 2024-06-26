package it.polimi.demo.network.rmi;

import it.polimi.demo.network.utils.StaticPrinter;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.utils.PingSender;
import it.polimi.demo.view.dynamic.ClientInterface;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.view.dynamic.GameDynamic;

import java.io.IOException;
import java.io.Serial;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

/**
 * RMI client class that implements the ClientInterface.
 * This class handles client-side operations for creating, joining, and managing games via RMI.
 */
public class RMIClient implements ClientInterface {

    /**
     * Serial version UID for serialization
     */
    @Serial
    private static final long serialVersionUID = 5576020039171499792L;

    /**
     * The reference to the main controller interface
     */
    private static MainControllerInterface asks;

    /**
     * The game controller interface
     */
    private GameControllerInterface controller = null;

    /**
     * The ID of the game
     */
    private int game_id;

    /**
     * The nickname of the player
     */
    private String nickname;

    /**
     * The RMI registry
     */
    private Registry registry;

    /**
     * The observer manager for handling game listeners
     */
    private final GameDynamic dynamic;

    /**
     * The listener for RMI callbacks
     */
    private static Listener lis;

    /**
     * Flag indicating whether the client has been initialized
     */
    private boolean initialized = false;

    /**
     * The ping sender for sending periodic pings to the server
     */
    private final PingSender ping;

    /**
     * Constructor that initializes the RMIClient.
     */
    public RMIClient(GameDynamic dynamic) {
        this.dynamic = dynamic;
        this.ping = new PingSender(this);
        this.ping.start();
        initialize();
    }

    /**
     * Initializes the RMI client by connecting to the registry and looking up the server.
     */
    private void initialize() {
        try {
            registry = LocateRegistry.getRegistry(Constants.serverIp, Constants.RMI_port);
            asks = (MainControllerInterface) registry.lookup(Constants.RMI_server_name);
            lis = (Listener) UnicastRemoteObject.exportObject(dynamic, 0);
            StaticPrinter.staticPrinter("Client RMI ready");
        } catch (Exception e) {
            StaticPrinter.staticPrinter("Connection failed");
        }
    }

    /**
     * Executes an action with the RMI registry.
     *
     * @param action the action to execute
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    private void withRegistry(Consumer<Registry> action) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(Constants.serverIp, Constants.RMI_port);
        asks = (MainControllerInterface) registry.lookup(Constants.RMI_server_name);
        action.accept(registry);
    }

    /**
     * Creates a new game.
     *
     * @param nick the nickname of the player
     * @param num_of_players the number of players in the game
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void createGame(String nick, int num_of_players) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                controller = asks.createGame(lis, nick, num_of_players);
                nickname = nick;
                game_id = controller.getGameId();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Joins an existing game.
     *
     * @param nick the nickname of the player
     * @param idGame the ID of the game to join
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                controller = asks.joinGame(lis, nick, idGame);
                if (controller != null) {
                    nickname = nick;
                    game_id = controller.getGameId();
                    initialized = controller.getNumConnectedPlayers() == controller.getNumPlayersToPlay();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Joins a random game.
     *
     * @param nick the nickname of the player
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void joinRandomly(String nick) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                controller = asks.joinRandomly(lis, nick);
                if (controller != null) {
                    nickname = nick;
                    game_id = controller.getGameId();
                    initialized = controller.getNumConnectedPlayers() == controller.getNumPlayersToPlay();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sets the player as ready for the game.
     *
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void setAsReady() throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.setAsReady(lis, nickname, game_id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Places the starter card for the game.
     *
     * @param orientation the orientation of the card
     * @throws RemoteException if an RMI error occurs
     * @throws GameEndedException if the game has ended
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException, GameEndedException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.placeStarterCard(lis, nickname, orientation, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Chooses a card in the game.
     *
     * @param which_card the index of the card to choose
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void chooseCard(int which_card) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.chooseCard(lis, nickname, which_card, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Places a card in the game.
     *
     * @param x the x coordinate to place the card
     * @param y the y coordinate to place the card
     * @param orientation the orientation of the card
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void placeCard(int x, int y, Orientation orientation) throws RemoteException, NotBoundException, GameEndedException {
        withRegistry(reg -> {
            try {
                asks.placeCard(lis, nickname, x, y, orientation, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Draws a card in the game.
     *
     * @param index the index of the card to draw
     * @throws RemoteException if an RMI error occurs
     * @throws GameEndedException if the game has ended
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void drawCard(int index) throws RemoteException, GameEndedException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.drawCard(lis, nickname, index, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sends a message in the game.
     *
     * @param nick the nickname of the player
     * @param msg the message to send
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void sendMessage(String nick, Message msg) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.sendMessage(lis, nick, msg, game_id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Shows the personal board of the player.
     * @param player_index the index of the player
     * @throws NotBoundException if the server name is not bound
     * @throws RemoteException if an RMI error occurs
     */
    @Override
    public void showOthersPersonalBoard(int player_index) throws NotBoundException, RemoteException {
        withRegistry(reg -> {
            try {
                asks.showOthersPersonalBoard(lis, nickname, player_index, game_id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sends a ping to the server.
     *
     * @throws RemoteException if an RMI error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void ping() throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.addPing(lis, nickname, game_id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Leaves the game.
     *
     * @param nick the nickname of the player
     * @param idGame the ID of the game to leave
     * @throws IOException if an IO error occurs
     * @throws NotBoundException if the server name is not bound
     */
    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.leaveGame(lis, nick, idGame);
                controller = null;
                nickname = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

