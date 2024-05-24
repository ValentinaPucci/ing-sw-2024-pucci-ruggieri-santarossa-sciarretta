package it.polimi.demo.networking.socket.client;


import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.GameListenerHandlerClient;
import it.polimi.demo.networking.HeartbeatSender;
import it.polimi.demo.networking.socket.client.gameControllerMessages.*;
import it.polimi.demo.networking.socket.client.mainControllerMessages.SocketClientMessageCreateGame;
import it.polimi.demo.networking.socket.client.mainControllerMessages.SocketClientMessageJoinGame;
import it.polimi.demo.networking.socket.client.mainControllerMessages.SocketClientMessageLeave;
import it.polimi.demo.networking.socket.client.mainControllerMessages.SocketClientMessageReconnect;
import it.polimi.demo.networking.socket.client.serverToClientMessages.SocketServerGenericMessage;
import it.polimi.demo.view.flow.CommonClientActions;
import it.polimi.demo.view.flow.Flow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

public class ClientSocket extends Thread implements CommonClientActions {

    /**
     * This is the socket that represents the client
     */
    private Socket clientSoc;

    /**
     * This is the output stream
     */
    private ObjectOutputStream ob_out;
    /**
     * This is the input stream
     */
    private ObjectInputStream ob_in;
    /**
     * This is the nickname associated with the client
     */
    private String nickname;
    /**
     * This is the gameListner we use to perform every action requested by the server
     */
    private final GameListenerHandlerClient modelInvokedEvents;
    /**
     *
     */
    private final HeartbeatSender socketHeartbeat;
    /**
     *
     */
    private Flow flow;

    public ClientSocket(Flow flow) {
        this.flow = flow;
        startConnection(DefaultValues.serverIp, DefaultValues.Default_port_Socket);
        modelInvokedEvents =  new GameListenerHandlerClient(flow);
        this.start();
        socketHeartbeat = new HeartbeatSender(flow,this);
    }

    /**
     * Reads all the incoming network traffic and execute the requested action
     */
    public void run() {
        while (true) {
            try {
                SocketServerGenericMessage msg = (SocketServerGenericMessage) ob_in.readObject();
                msg.execute(modelInvokedEvents);

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
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                // New socket
                clientSoc = new Socket(ip, port);

                // Attach the output stream to the socket
                ob_out = new ObjectOutputStream(clientSoc.getOutputStream());
                // Attach the input stream to the socket
                ob_in = new ObjectInputStream(clientSoc.getInputStream());
                retry = false;
            } catch (IOException e) {
                if (!retry) {
                    printAsync("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to Socket Server on port: '" + port + "' with ip: '" + ip + "'");

                i = 0;
                while (i < DefaultValues.seconds_between_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printAsyncNoLine(".");
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= DefaultValues.num_of_attempt_to_connect_toServer_before_giveup) {
                    printAsyncNoLine("Give up reconnection, to many attempts!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry);

    }

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

    @Override
    public void createGame(String nickname, int num_of_players) throws IOException, InterruptedException, NotBoundException {
        ob_out.writeObject(new SocketClientMessageCreateGame(nickname, num_of_players));
        finishSending();
        if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException {
        ob_out.writeObject(new SocketClientMessageJoinGame(nick, idGame));
        finishSending();
        if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }

    @Override
    public void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException {
        ob_out.writeObject(new SocketClientMessageReconnect(nick, idGame));
        finishSending();
        if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }
    }

    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {
        ob_out.writeObject(new SocketClientMessageLeave(nick, idGame));
        finishSending();
        nickname=null;
        if(socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }
    }

    @Override
    public void setAsReady() throws IOException {
        ob_out.writeObject(new SocketClientMessageSetReady(nickname));
        finishSending();
    }

    @Override
    public boolean isMyTurn() throws RemoteException {
        return false;
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws IOException, GameEndedException, NotBoundException {
        ob_out.writeObject(new SocketClientMessagePlaceStarterCard(orientation));
        finishSending();

    }

    @Override
    public void chooseCard(int which_card) throws IOException {
        ob_out.writeObject(new SocketClientMessageChooseCard(which_card));
        finishSending();

    }

    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws IOException {
        ob_out.writeObject(new SocketClientMessagePlaceCard(where_to_place_x, where_to_place_y, orientation));
        finishSending();
    }

    @Override
    public void drawCard(int index) throws IOException, GameEndedException {
        ob_out.writeObject(new SocketClientMessageDrawCard(index));
        finishSending();
    }

    @Override
    public void sendMessage(Message msg) throws RemoteException {
        try {
            ob_out.writeObject(new SocketClientMessageNewChatMessage(msg));
            finishSending();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void heartbeat() throws RemoteException {
        if (ob_out != null) {
            try {
                ob_out.writeObject(new SocketClientMessageHeartBeat(nickname));
                finishSending();
            } catch (IOException e) {
                printAsync("Connection lost to the server!! Impossible to send heartbeat...");
            }
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
