package it.polimi.demo.networking.socket.client;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.ConnectionSuccessfulException;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.ObserverManagerClient;
import it.polimi.demo.networking.PingSender;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.socket.client.gameControllerMessages.*;
import it.polimi.demo.networking.socket.client.mainControllerMessages.*;
import it.polimi.demo.networking.socket.client.serverToClientMessages.SocketServerGenericMessage;
import it.polimi.demo.view.flow.ClientInterface;
import it.polimi.demo.view.flow.Dynamics;

import java.net.Socket;
import java.io.*;
import java.util.stream.IntStream;


import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

public class ClientSocket extends Thread implements ClientInterface {

    /**
     * This is the socket that corresponds to the client
     */
    private transient Socket ClientSocket;

    /**
     * This is the output stream
     */
    private transient ObjectOutputStream ob_out;
    /**
     * This is the input stream
     */
    private transient ObjectInputStream ob_in;
    /**
     * This is the nickname associated with the client
     */
    private String nickname;

    /**
     * This is the gameController associated with the game
     */
    private GameControllerInterface gameController = null;
    /**
     * This is the game_id associated with the game
     */
    private int game_id;
    private final ObserverManagerClient modelEvents;
    private final transient PingSender socketHeartbeat;
    /**
     * This is the dynamics of the game
     */
    private Dynamics dynamics;

    public ClientSocket(Dynamics dynamics) {
        this.dynamics = dynamics;
        initiateConnection(DefaultValues.serverIp, DefaultValues.Default_port_Socket);
        modelEvents =  new ObserverManagerClient(dynamics);
        this.start();
        socketHeartbeat = new PingSender(dynamics,this);
    }

    /**
     * Run for the ClientSocket, it reads the messages from the server and performs the action requested
     */
    public void run() {
        while (true) {
            try {
                it.polimi.demo.networking.socket.client.serverToClientMessages.SocketServerGenericMessage msg = (SocketServerGenericMessage) ob_in.readObject();
                msg.perform(modelEvents);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                printAsync("[ERROR] Connection to server lost! " + e);
                try {
                    System.in.read();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(-1);
            }
        }
    }

    /**
     * Start the Connection to the Socket Server
     *
     * @param serverIp of the Socket server to connect
     * @param serverPort of the Socket server to connect
     */
    private void initiateConnection(String serverIp, int serverPort) {
        boolean connectionFailed;
        int connectionAttempts = 0;

        do {
            connectionFailed = false;
            try {
                // Establish new socket connection
                ClientSocket = new Socket(serverIp, serverPort);
                // Initialize output stream
                ob_out = new ObjectOutputStream(ClientSocket.getOutputStream());
                // Initialize input stream
                ob_in = new ObjectInputStream(ClientSocket.getInputStream());
            } catch (IOException ioException) {
                connectionFailed = true;
                connectionAttempts++;
                printAsync("[ERROR] Failed to connect to the server: " + ioException + "\n");
                printAsyncNoLine("[Attempt #" + connectionAttempts + "] Retrying connection to server at port: '" + serverPort + "' and IP: '" + serverIp + "'");

                for (int i = 0; i < DefaultValues.seconds_between_reconnection; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        throw new RuntimeException(interruptedException);
                    }
                    printAsyncNoLine(".");
                }
                printAsyncNoLine("\n");

                if (connectionAttempts >= DefaultValues.num_of_attempt_to_connect_toServer_before_giveup) {
                    printAsyncNoLine("Aborting reconnection, too many failed attempts!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
            }
        } while (connectionFailed);
    }

    /**
     * it closes the connection
     *
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        ob_in.close();
        ob_out.close();
        ClientSocket.close();
        if(socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }
    }

    /**
     * Generic method used to send a message to the server
     *
     * @param message to send
     * @throws IOException
     */
    private void sendMessage(Object message) throws IOException {
        ob_out.writeObject(message);
        
        ob_out.flush();
        ob_out.reset();
    }

    /**
     * Method used to call the createGame action
     * @param nickname
     * @param num_of_players
     * @throws IOException
     */
    @Override
    public void createGame(String nickname, int num_of_players) throws IOException {
        this.nickname = nickname;
        sendMessage(new SocketClientMsgGameCreation(nickname, num_of_players));
        startHeartbeat();
    }

    /**
     * Method used to call the joinGame action
     * @param nick
     * @param idGame
     * @throws IOException
     */
    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        nickname = nick;
        sendMessage(new SocketClientMsgJoinGame(nick, idGame));
        startHeartbeat();
    }

    /**
     * Method used to call the joinFirstAvailableGame action
     * @param nick
     * @throws IOException
     */
    @Override
    public void joinFirstAvailableGame(String nick) throws IOException {
        nickname = nick;
        sendMessage(new SocketClientMessageJoinFirstAvailableGame(nick));
        startHeartbeat();
    }

    /**
     * Method used to call the leaveGame action
     * @param nick
     * @param idGame
     * @throws IOException
     */
    @Override
    public void leave(String nick, int idGame) throws IOException {
        sendMessage(new SocketClientMsgLeaveGame(nick, idGame));
        nickname = null;
        stopHeartbeat();
    }

    /**
     * Method used to set the player as ready
     * @throws IOException
     */
    @Override
    public void setAsReady() throws IOException {
        sendMessage(new SocketClientMsgSetReady(nickname));
    }

    /**
     * Method used to place the starter card
     * @param orientation
     * @throws IOException
     */
    @Override
    public void placeStarterCard(Orientation orientation) throws IOException, GameEndedException {
        sendMessage(new SocketClientMsgPlaceStarterCard(orientation));
    }

    /**
     * Method used to choose a card
     * @param which_card
     * @throws IOException
     */
    @Override
    public void chooseCard(int which_card) throws IOException {
        sendMessage(new SocketClientMsgChooseCard(which_card));
    }

    /**
     * Method used to place a card
     * @param where_to_place_x
     * @param where_to_place_y
     * @param orientation
     * @throws IOException
     */
    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws IOException {
        sendMessage(new SocketClientMsgPlaceCard(where_to_place_x, where_to_place_y, orientation));
    }

    /**
     * Method used to draw a card
     * @param index
     * @throws IOException
     */
    @Override
    public void drawCard(int index) throws IOException, GameEndedException {
        sendMessage(new SocketClientMsgDrawCard(index));
    }

    /**
     * Method used to send a chat message
     * @param receiver
     * @param msg
     * @throws IOException
     */
    @Override
    public void sendMessage(String receiver, Message msg) throws IOException {
        sendMessage(new SocketClientMsgSendMessage(receiver, msg));
    }

    @Override
    public void heartbeat() {
        //        if (ob_out != null) {
//            try {
//                ob_out.writeObject(new SocketClientMessageHeartBeat(nickname));
//                finishSending();
//            } catch (IOException e) {
//                printAsync("Connection lost to the server!! Impossible to send heartbeat...");
//            }
//        }
    }

    private void startHeartbeat() {
        if (!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }

    private void stopHeartbeat() {
        if (socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }
    }
}
