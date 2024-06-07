package it.polimi.demo.networking.socket.client;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.ObserverManagerClient;
import it.polimi.demo.networking.socket.client.gameControllerMessages.*;
import it.polimi.demo.networking.socket.client.mainControllerMessages.*;
import it.polimi.demo.networking.socket.client.serverToClientMessages.SocketServerGenericMessage;
import it.polimi.demo.view.flow.ClientInterface;
import it.polimi.demo.view.flow.Dynamics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.stream.IntStream;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

public class ClientSocket extends Thread implements ClientInterface, Serializable {
    private static final long serialVersionUID = 1L;

    private transient Socket socket;
    private transient ObjectOutputStream outputStream;
    private transient ObjectInputStream inputStream;
    private String userNickname;
    private final ObserverManagerClient eventManager;

    public ClientSocket(Dynamics clientDynamics) {
        this.eventManager = new ObserverManagerClient(clientDynamics);
        initiateConnection(DefaultValues.serverIp, DefaultValues.Default_port_Socket);
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                SocketServerGenericMessage message = (SocketServerGenericMessage) inputStream.readObject();
                message.execute(eventManager);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                handleDisconnection(e);
            }
        }
    }

    private void initiateConnection(String ipAddress, int port) {
        boolean connected = IntStream.rangeClosed(1, DefaultValues.num_of_attempt_to_connect_toServer_before_giveup)
                .anyMatch(attempt -> tryToConnect(ipAddress, port, attempt));

        if (!connected) {
            printAsyncNoLine("Giving up reconnection after too many attempts!");
            awaitExit();
        }
    }

    private boolean tryToConnect(String ipAddress, int port, int attempt) {
        try {
            socket = new Socket(ipAddress, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            if (attempt == 1) {
                printAsync("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient exception: " + e + "\n");
            }
            printAsyncNoLine("[#" + attempt + "] Retrying connection to Socket Server at port: '" + port + "' with IP: '" + ipAddress + "'");
            pause(DefaultValues.seconds_between_reconnection);
            printAsyncNoLine("\n");
            return false;
        }
    }

    private void handleDisconnection(Exception e) {
        printAsync("[ERROR] Connection to server lost! " + e);
        awaitExit();
        System.exit(-1);
    }

    private void awaitExit() {
        try {
            System.in.read();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void pause(int seconds) {
        IntStream.range(0, seconds).forEach(i -> {
            try {
                Thread.sleep(1000);
                printAsyncNoLine(".");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void createGame(String nickname, int numberOfPlayers) throws IOException {
        this.userNickname = nickname;
        outputStream.writeObject(new SocketClientMessageCreateGame(nickname, numberOfPlayers));
        finishSending();
    }

    @Override
    public void joinGame(String nickname, int gameId) throws IOException {
        this.userNickname = nickname;
        outputStream.writeObject(new SocketClientMessageJoinGame(nickname, gameId));
        finishSending();
    }

    @Override
    public void joinFirstAvailableGame(String nickname) throws IOException {
        this.userNickname = nickname;
        outputStream.writeObject(new SocketClientMessageJoinFirstAvailableGame(nickname));
        finishSending();
    }

    @Override
    public void leave(String nickname, int gameId) throws IOException, NotBoundException {
        outputStream.writeObject(new SocketClientMessageLeave(nickname, gameId));
        finishSending();
        this.userNickname = null;
    }

    @Override
    public void setAsReady() throws IOException, NotBoundException {
        outputStream.writeObject(new SocketClientMessageSetReady(userNickname));
        finishSending();
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws IOException, GameEndedException, NotBoundException {
        outputStream.writeObject(new SocketClientMessagePlaceStarterCard(orientation));
        finishSending();
    }

    @Override
    public void chooseCard(int cardIndex) throws IOException {
        outputStream.writeObject(new SocketClientMessageChooseCard(cardIndex));
        finishSending();
    }

    @Override
    public void placeCard(int xCoordinate, int yCoordinate, Orientation orientation) throws IOException {
        outputStream.writeObject(new SocketClientMessagePlaceCard(xCoordinate, yCoordinate, orientation));
        finishSending();
    }

    @Override
    public void drawCard(int cardIndex) throws IOException, GameEndedException {
        outputStream.writeObject(new SocketClientMessageDrawCard(cardIndex));
        finishSending();
    }

    @Override
    public void sendMessage(String receiver, Message message) throws IOException, NotBoundException {
        outputStream.writeObject(new SocketClientMessageSendMessage(receiver, message));
        finishSending();
    }

    @Override
    public void heartbeat() {
        // Heartbeat logic if needed
    }

    private void finishSending() throws IOException {
        outputStream.flush();
        outputStream.reset();
    }
}


