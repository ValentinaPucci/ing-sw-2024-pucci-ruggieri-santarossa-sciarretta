package it.polimi.demo.network.socket.client;

import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;

import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.ObserverManagerClient;
import it.polimi.demo.network.PingSender;
import it.polimi.demo.network.socket.client.gameControllerMessages.*;
import it.polimi.demo.network.socket.client.mainControllerMessages.*;
import it.polimi.demo.network.socket.client.serverToClientMessages.SocketServerGenericMessage;
import it.polimi.demo.network.socket.client.mainControllerMessages.SocketClientMessageJoinFirstAvailableGame;
import it.polimi.demo.view.dynamic.ClientInterface;
import it.polimi.demo.view.dynamic.Dynamic;

import java.net.Socket;
import java.io.*;
import java.rmi.NotBoundException;

import static it.polimi.demo.view.text.PrintAsync.printAsync;
import static it.polimi.demo.view.text.PrintAsync.printAsyncNoLine;

public class ClientSocket extends Thread implements ClientInterface {

    private transient Socket clientSocket;
    private transient ObjectOutputStream ob_out;
    private transient ObjectInputStream ob_in;
    private String nickname;
    private final ObserverManagerClient modelEvents;
    private final transient PingSender socketHeartbeat;
    private Dynamic dynamics;

    public ClientSocket(Dynamic dynamics) {
        this.dynamics = dynamics;
        modelEvents = new ObserverManagerClient(dynamics);
        initiateConnection(Constants.serverIp, Constants.Socket_port);
        this.start();
        socketHeartbeat = new PingSender(dynamics, this);
    }

    public void run() {
        try {
            while (true) {
                try {
                    SocketServerGenericMessage msg = (SocketServerGenericMessage) ob_in.readObject();
                    msg.perform(modelEvents);
                } catch (IOException | ClassNotFoundException e) {
                    printAsync("[ERROR] Connection to server lost! " + e);
                    break;
                } catch (InterruptedException e) {
                    printAsync("[ERROR] Thread interrupted! " + e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (ob_in != null) ob_in.close();
            if (ob_out != null) ob_out.close();
            if (clientSocket != null) clientSocket.close();
            if (socketHeartbeat != null && socketHeartbeat.isAlive()) {
                socketHeartbeat.interrupt();
            }
        } catch (IOException e) {
            printAsync("[ERROR] Error closing resources: " + e);
        }
    }


    private void initiateConnection(String serverIp, int serverPort) {
        boolean connectionFailed;
        int connectionAttempts = 0;

        do {
            connectionFailed = false;
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

            } catch (IOException ioException) {
                connectionFailed = true;
                connectionAttempts++;
                printAsync("[ERROR] Failed to connect to the server: " + ioException + "\n");
                printAsyncNoLine("[Attempt #" + connectionAttempts + "] Retrying connection to server at port: '" + serverPort + "' and IP: '" + serverIp + "'");

                for (int i = 0; i < Constants.seconds_between_reconnection; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        throw new RuntimeException(interruptedException);
                    }
                    printAsyncNoLine(".");
                }
                printAsyncNoLine("\n");

                if (connectionAttempts >= Constants.num_of_attempt_to_connect_toServer_before_giveup) {
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


    private void sendMessage(Object message) throws IOException {
        ob_out.writeObject(message);
        ob_out.flush(); // Here I write the data from the buffer to the socket
        ob_out.reset();
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
    public void joinRandomly(String nick) throws IOException, InterruptedException, NotBoundException {
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
    public void ping() throws IOException, NotBoundException {
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
