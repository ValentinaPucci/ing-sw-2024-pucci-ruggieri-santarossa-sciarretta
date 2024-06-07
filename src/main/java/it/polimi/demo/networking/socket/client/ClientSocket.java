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
     * This is the socket that represents the client
     */
    private transient Socket clientSoc;

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
    /**
     * This is the gameListner we use to perform every action requested by the server
     */
    private final ObserverManagerClient modelInvokedEvents;
    /**
     *
     */
    private final transient PingSender socketHeartbeat;
    /**
     * This is the dynamics of the game
     */
    private Dynamics dynamics;

    public ClientSocket(Dynamics dynamics) {
        this.dynamics = dynamics;
        startConnection(DefaultValues.serverIp, DefaultValues.Default_port_Socket);
        modelInvokedEvents =  new ObserverManagerClient(dynamics);
        this.start();
        socketHeartbeat = new PingSender(dynamics,this);
    }

    /**
     * Reads all the incoming network traffic and execute the requested action
     */
    public void run() {
        while (true) {
            try {
                it.polimi.demo.networking.socket.client.serverToClientMessages.SocketServerGenericMessage msg = (SocketServerGenericMessage) ob_in.readObject();
                msg.perform(modelInvokedEvents);

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
     * @param ip of the Socket server to connect
     * @param port of the Socket server to connect
     */
    private void startConnection(String ip, int port) {
        IntStream.iterate(1, i -> i + 1)
                .limit(DefaultValues.num_of_attempt_to_connect_toServer_before_giveup)
                .forEach(attempt -> {
                    try {
                        // New socket
                        clientSoc = new Socket(ip, port);

                        // Attach the output stream to the socket
                        ob_out = new ObjectOutputStream(clientSoc.getOutputStream());
                        // Attach the input stream to the socket
                        ob_in = new ObjectInputStream(clientSoc.getInputStream());

                        // Break the stream if connection is successful
                        throw new ConnectionSuccessfulException();
                    } catch (IOException e) {
                        if (attempt == 1) {
                            printAsync("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient RMI exception: " + e + "\n");
                        }
                        printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to Socket Server on port: '" + port + "' with ip: '" + ip + "'");

                        IntStream.range(0, DefaultValues.seconds_between_reconnection).forEach(i -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            printAsyncNoLine(".");
                        });

                        printAsyncNoLine("\n");

                        if (attempt >= DefaultValues.num_of_attempt_to_connect_toServer_before_giveup) {
                            printAsyncNoLine("Give up reconnection, too many attempts!");
                            try {
                                System.in.read();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            System.exit(-1);
                        }
                    }
                });
    }
    // Custom exception to indicate successful connection

    /**
     * Close the connection
     *
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        ob_in.close();
        ob_out.close();
        clientSoc.close();
        if(socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }
    }

    private void sendMessage(Object message) throws IOException {
        ob_out.writeObject(message);
        finishSending();
    }


    @Override
    public void createGame(String nickname, int num_of_players) throws IOException {
        this.nickname = nickname;
        sendMessage(new SocketClientMsgGameCreation(nickname, num_of_players));
        startHeartbeat();
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException {
        nickname = nick;
        sendMessage(new SocketClientMsgJoinGame(nick, idGame));
        startHeartbeat();
    }

    @Override
    public void joinFirstAvailableGame(String nick) throws IOException {
        nickname = nick;
        sendMessage(new SocketClientMessageJoinFirstAvailableGame(nick));
        startHeartbeat();
    }

    @Override
    public void leave(String nick, int idGame) throws IOException {
        sendMessage(new SocketClientMsgLeaveGame(nick, idGame));
        nickname = null;
        stopHeartbeat();
    }

    @Override
    public void setAsReady() throws IOException {
        sendMessage(new SocketClientMsgSetReady(nickname));
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws IOException, GameEndedException {
        sendMessage(new SocketClientMsgPlaceStarterCard(orientation));
    }

    @Override
    public void chooseCard(int which_card) throws IOException {
        sendMessage(new SocketClientMsgChooseCard(which_card));
    }

    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws IOException {
        sendMessage(new SocketClientMsgPlaceCard(where_to_place_x, where_to_place_y, orientation));
    }

    @Override
    public void drawCard(int index) throws IOException, GameEndedException {
        sendMessage(new SocketClientMsgDrawCard(index));
    }

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

    /**
     * Makes sure the message has been sent
     * @throws IOException
     */
    private void finishSending() throws IOException {
        ob_out.flush();
        ob_out.reset();
    }
}
