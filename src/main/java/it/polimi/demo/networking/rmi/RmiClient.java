package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.GameController;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.chat.Message;

import java.io.IOException;
import java.io.Serializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.demo.networking.PrintAsync.*;
import static org.fusesource.jansi.Ansi.ansi;

// Todo: re-implement some code (originality)

/**
 * RMI client to communicate with the remote server using RMI.
 * Implements the MainControllerInterface to receive messages from the server.
 */
public class RmiClient extends UnicastRemoteObject implements VirtualClient, Serializable {

    /**
     * The remote object returned by the registry that represents the main controller
     */
    private static MainControllerInterface client_requests;
    /**
     * The remote object returned by the RMI server that represents the connected game
     */
    private GameControllerInterface gameController = null;
    /**
     * The remote object on which the server will invoke remote methods
     */
    private static GameListener events_from_model;
    /**
     * The nickname associated to the client (!=null only when connected in a game)
     */
    private String nickname;
    /**
     * The remote object on which the server will invoke remote methods
     */
    //private final GameListenersHandlerClient gameListenersHandler;
    /**
     * Registry of the RMI
     */
    private Registry registry;

    /**
     * Create, start and connect an RMI Client to the server
     */
    public RmiClient() throws RemoteException {
        super();
        //gameListenersHandler = new GameListenersHandlerClient(flow);
        connectToRMIServer();

        //rmiHeartbeat = new HeartbeatSender(flow,this);
        //rmiHeartbeat.start();
    }

    /**
     * Connects to the RMI server.
     */
    public void connectToRMIServer() {
        int attempt = 0;

        while (attempt < DefaultValues.max_num_connection_attempts) {
            try {
                // Get the registry for the RMI server
                Registry registry = LocateRegistry.getRegistry(DefaultValues.Server_ip, DefaultValues.Default_port_RMI);

                // Look up the MainControllerInterface from the registry
                MainControllerInterface controllerInterface = (MainControllerInterface) registry.lookup(DefaultValues.RMI_ServerName);

                // Export the GameListener object
                //events_from_model = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

                // Print a success message
                printAsync("Connected to RMI server.");

                // Exit the loop as connection succeeded
                break;
            } catch (RemoteException | NotBoundException e) {
                // Print error message for the first attempt only
                if (attempt == 0) {
                    printAsync("[ERROR] Unable to connect to RMI server:");
                    e.printStackTrace();
                }

                // Print reconnection attempt message
                printAsyncNoLine("[Attempt #" + (attempt + 1) + "] Waiting to reconnect to RMI server...");

                // Wait for a certain duration before attempting reconnection
                waitForReconnection();

                // Increment the attempt counter
                attempt++;
            }
        }

        // If all attempts failed, exit the program
        if (attempt == DefaultValues.max_num_connection_attempts) {
            printAsyncNoLine("Max connection attempts reached. Exiting...");
            System.exit(-1);
        }
    }

    /**
     * Waits for a certain duration before attempting reconnection.
     */
    private void waitForReconnection() {
        try {
            // Wait for a certain duration before attempting reconnection
            Thread.sleep(DefaultValues.secondsToWaitReconnection * 1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    //--------------------Overrides of VirtualClient interface-------------------------------------------------

    // subsection: MainControllerInterface

    /**
     * Requests the creation of a game on the server.
     *
     * @param nick        the nickname of the player creating the game
     * @param num_players the number of players in the game
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void createGame(String nick, int num_players) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = client_requests.createGame(events_from_model, nick, num_players);
        nickname = nick;
    }

    /**
     * Requests to join the first available game on the server.
     *
     * @param nick the nickname of the player joining the game
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void joinFirstAvailable(String nick) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = client_requests.joinFirstAvailableGame(events_from_model, nick);
        nickname = nick;
    }

    /**
     * Requests to join a specific game on the server.
     *
     * @param nick   the nickname of the player joining the game
     * @param idGame the ID of the game to join
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = client_requests.joinGame(events_from_model, nick, idGame);
        nickname = nick;
    }

    /**
     * Requests to reconnect to a specific game on the server.
     *
     * @param nick   the nickname of the player reconnecting
     * @param idGame the ID of the game to reconnect to
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void reconnect(String nick, int idGame) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = client_requests.reconnect(events_from_model, nick, idGame);
        nickname = nick;
    }

    /**
     * Requests to leave a game on the server.
     *
     * @param nick   the nickname of the player leaving the game
     * @param idGame the ID of the game to leave
     * @throws IOException if an I/O exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {
        connectToRegistry();
        client_requests.leaveGame(events_from_model, nick, idGame);
        gameController = null;
        nickname = null;
    }

    /**
     * Connects to the RMI registry.
     *
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    private void connectToRegistry() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.Server_ip, DefaultValues.Default_port_RMI);
        client_requests = (MainControllerInterface) registry.lookup(DefaultValues.RMI_ServerName);
    }

    // subsection: GameControllerInterface

    /**
     * Send a message to the server
     *
     * @param msg message to send
     * @throws RemoteException if the connection fails
     */
    @Override
    public void sendMessage(Message msg) throws RemoteException {
        gameController.sendMessage(msg);
    }

    /**
     * Notify the server that a client is ready to start
     *
     * @throws RemoteException if the connection fails
     */
    @Override
    public void setAsReady() throws RemoteException {
        if (gameController != null) {
            gameController.setPlayerAsReadyToStart(nickname);
        }
    }

    /**
     * Check if this is my turn
     * @return true if it's my turn, false else
     * @throws RemoteException if the connection fails
     */
    @Override
    public boolean isMyTurn() throws RemoteException {
        return gameController.isMyTurn(nickname);
    }

    /**
     * Draw a card from the deck/table
     * @param player_nickname The nickname of the player who wants to draw a card
     *
     * @param index The index indicating which card to draw:
     *                    1: Resource Deck
     *                    2: First Resource Card on the table
     *                    3: Second Resource Card on the table
     *                    4: Gold Deck
     *                    5: First Gold Card on the table
     *                    6: Second Gold Card on the table
     *
     * @throws IOException if the connection fails
     */
    @Override
    public void drawCard(String player_nickname, int index) throws IOException {
        gameController.drawCard(player_nickname, index);
    }

    /**
     * Place a resource card on the personal board of the player
     * @param card_chosen the card to place
     * @param x the x coordinate on the personal board
     * @param y the y coordinate on the personal board
     * @throws IOException if the connection fails
     */
    @Override
    public void placeCard(ResourceCard card_chosen, int x, int y) throws IOException{
        gameController.placeCard(card_chosen, gameController.getPlayerEntity(nickname), x, y);
    }

    /**
     * Place a gold card on the personal board of the player
     * @param card_chosen the card to place
     * @param x the x coordinate on the personal board
     * @param y the y coordinate on the personal board
     * @throws IOException if the connection fails
     */
    @Override
    public void placeCard(GoldCard card_chosen, int x, int y) throws IOException{
        gameController.placeCard(card_chosen, gameController.getPlayerEntity(nickname), x, y);
    }

    /**
     * Send a PING to the server
     *
     * @throws RemoteException if the connection fails
     */
    @Override
    public void addPing() throws RemoteException {
        if (gameController != null) {
            gameController.addPing(nickname, events_from_model);
        }
    }
}
