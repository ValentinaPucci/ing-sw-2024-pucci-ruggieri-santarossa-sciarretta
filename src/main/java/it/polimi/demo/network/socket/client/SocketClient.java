package it.polimi.demo.network.socket.client;

import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;

import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.utils.PingSender;
import it.polimi.demo.network.socket.client.ClientToServerMessages.*;
import it.polimi.demo.network.socket.client.ServerToClientMessages.S2CGenericMessage;
import it.polimi.demo.network.socket.client.ClientToServerMessages.MCMsgJoinFirstAvailableGame;
import it.polimi.demo.network.socket.client.ClientToServerMessages.MCMsgGameCreation;
import it.polimi.demo.network.socket.client.ClientToServerMessages.MCMsgJoinGame;
import it.polimi.demo.network.socket.client.ClientToServerMessages.MCMsgLeaveGame;
import it.polimi.demo.view.dynamic.ClientInterface;
import it.polimi.demo.view.dynamic.GameDynamic;

import java.net.Socket;
import java.io.*;
import java.rmi.NotBoundException;

import static it.polimi.demo.network.utils.StaticPrinter.staticPrinter;

/**
 * Client socket is together with clientConnection the main class for the client side of the socket connection.
 */
public class SocketClient extends Thread implements ClientInterface {

    /**
     * Socket used to connect to the server.
     */
    private transient Socket clientSocket;
    /**
     * ObjectOutputStream used to send messages to the server.
     */
    private transient ObjectOutputStream ob_out;
    /**
     * ObjectInputStream used to receive messages from the server.
     */
    private transient ObjectInputStream ob_in;
    /**
     * Nickname of the player.
     */
    private String nickname;
    /**
     * ObserverManagerClient used to manage the events received from the server.
     */
    private final GameDynamic dynamic;
    /**
     * PingSender used to send pings to the server, used to check the connection status.
     */
    private transient PingSender socketPing;

    public SocketClient(GameDynamic dynamic) {
        this.dynamic = dynamic;
        initializeConnection(Constants.serverIp, Constants.Socket_port);
        this.start();
        socketPing = new PingSender(this);
    }

    /**
     *  It is the main loop for receiving and processing messages from the server.
     *  This method is executed in a separate thread when the start method of the SocketClient class
     *  (which extends Thread) is called.
     */
    public void run() {
        boolean running = true;
        try {
            Runnable receiveAndProcess = () -> {
                try {
                    S2CGenericMessage msg = (S2CGenericMessage) ob_in.readObject();
                    msg.perform(dynamic);
                } catch (IOException | ClassNotFoundException e) {
                    staticPrinter("[ERROR] Connection to server lost! " + e);
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    staticPrinter("[ERROR] Thread interrupted! " + e);
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            };

            while (running) {
                try {
                    receiveAndProcess.run();
                } catch (RuntimeException e) {
                    running = false;
                }
            }
        } finally {
            closeResources();
        }
    }


    /**
     * Close all the resources used by the SocketClient.
     */
    private void closeResources() {
        try {
            if (ob_in != null) ob_in.close();
            if (ob_out != null) ob_out.close();
            if (clientSocket != null) clientSocket.close();
            if (socketPing != null && socketPing.isAlive()) {
                socketPing.interrupt();
            }
        } catch (IOException e) {
            staticPrinter("[ERROR] Error closing resources: " + e);
        }
    }

    /**
     * Initialize the connection with the server at the specified IP and port.
     * It uses a buffer in order to improve the performance of the communication.
     * @param serverIp IP of the server
     * @param serverPort Port of the server
     */
    private void initializeConnection(String serverIp, int serverPort) {

        try {
            // Establish new socket connection
            clientSocket = new Socket(serverIp, serverPort);

            // Buffered streams for better performance:
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());

            // Initialize output stream
            ob_out = new ObjectOutputStream(bufferedOutputStream);
            ob_out.flush();

            // Initialize input stream
            ob_in = new ObjectInputStream(bufferedInputStream);
        }
        catch (IOException e) {
            staticPrinter("[ERROR] Error initializing connection: " + e);
        }
    }

    /**
     * Send a generic (Object) message to the server, in order to perform the required action..
     * @param message
     * @throws IOException
     */
    private void sendMessage(Object message) throws IOException {
        ob_out.writeObject(message);
        ob_out.flush();
        ob_out.reset();
    }

    /**
     * Create a new game with the specified nickname and number of players.
     * @param nickname
     * @param num_of_players
     * @throws IOException
     */
    @Override
    public void createGame(String nickname, int num_of_players) throws IOException {
        this.nickname = nickname;
        sendMessage(new MCMsgGameCreation(nickname, num_of_players));
        startPing();
    }

    /**
     * Join an existing game with the specified nickname and game ID.
     * @param nick
     * @param idGame
     * @throws IOException
     */
    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        nickname = nick;
        sendMessage(new MCMsgJoinGame(nick, idGame));
        startPing();
    }

    /**
     * Join a game randomly with the specified nickname.
     * @param nick
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    @Override
    public void joinRandomly(String nick) throws IOException, InterruptedException, NotBoundException {
        nickname = nick;
        sendMessage(new MCMsgJoinFirstAvailableGame(nick));
        startPing();
    }

    /**
     * Leave the game with the specified nickname and game ID.
     * @param nick
     * @param idGame
     * @throws IOException
     */
    @Override
    public void leave(String nick, int idGame) throws IOException {
        sendMessage(new MCMsgLeaveGame(nick, idGame));
        nickname = null;
        stopPing();
    }

    /**
     * Set the player as ready to start the game.
     * @throws IOException
     */
    @Override
    public void setAsReady() throws IOException {
        sendMessage(new GCMsgSetReady(nickname));
    }

    /**
     * Place the starter card with the specified orientation.
     * @param orientation
     * @throws IOException
     * @throws GameEndedException
     */
    @Override
    public void placeStarterCard(Orientation orientation) throws IOException, GameEndedException {
        sendMessage(new GCMsgPlaceStarterCard(orientation));
    }

    /**
     * Choose a card with the specified index.
     * @param which_card
     * @throws IOException
     */
    @Override
    public void chooseCard(int which_card) throws IOException {
        sendMessage(new GCMsgChooseCard(which_card));
    }

    /**
     * Place a card with the specified coordinates and orientation.
     * @param where_to_place_x
     * @param where_to_place_y
     * @param orientation
     * @throws IOException
     */
    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws IOException {
        sendMessage(new GCMsgPlaceCard(where_to_place_x, where_to_place_y, orientation));
    }

    /**
     * Draw a card with the specified index.
     * @param index
     * @throws IOException
     * @throws GameEndedException
     */
    @Override
    public void drawCard(int index) throws IOException, GameEndedException {
        sendMessage(new GCMsgDrawCard(index));
    }

    /**
     * Show the personal board of the player with the specified index.
     * @param player_index
     * @throws IOException
     */
    @Override
    public void showOthersPersonalBoard(int player_index) throws IOException {
        sendMessage(new GCMsgShowOthersPersonalBoard(nickname, player_index));
    }

    /**
     * Send a message to the specified receiver,a real message.
     * @param receiver The receiver of the message.
     * @param msg The message to send.
     * @throws IOException
     */
    @Override
    public void sendMessage(String receiver, Message msg) throws IOException {
        sendMessage(new GCMsgSendMessage(receiver, msg));
    }

    @Override
    public void ping() throws IOException, NotBoundException {}

    private void startPing() {
        if (socketPing != null && socketPing.isAlive()) {
            socketPing.interrupt();
        }
        socketPing = new PingSender(this);
        socketPing.start();
    }

    private void stopPing() {
        if (socketPing.isAlive()) {
            socketPing.interrupt();
        }
    }
}
