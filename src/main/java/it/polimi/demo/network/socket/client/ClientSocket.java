package it.polimi.demo.network.socket.client;

import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.ObserverManagerClient;
import it.polimi.demo.network.StaticPrinter;
import it.polimi.demo.network.socket.client.gameControllerMessages.*;
import it.polimi.demo.network.socket.client.mainControllerMessages.*;
import it.polimi.demo.network.socket.client.serverToClientMessages.SocketServerGenericMessage;
import it.polimi.demo.view.dynamic.ClientInterface;
import it.polimi.demo.view.dynamic.Dynamic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.stream.IntStream;

import static it.polimi.demo.network.StaticPrinter.staticPrinter;
import static it.polimi.demo.network.StaticPrinter.staticPrinterNoNewLine;

public class ClientSocket extends Thread implements ClientInterface, Serializable {
    private static final long serialVersionUID = 1L;

    private transient Socket socket;
    private transient ObjectOutputStream outputStream;
    private transient ObjectInputStream inputStream;
    private String userNickname;
    private final ObserverManagerClient eventManager;

    public ClientSocket(Dynamic clientDynamic) {
        this.eventManager = new ObserverManagerClient(clientDynamic);
        initiateConnection(Constants.serverIp, Constants.Socket_port);
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                SocketServerGenericMessage message = (SocketServerGenericMessage) inputStream.readObject();
                message.perform(eventManager);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                handleDisconnection(e);
            }
        }
    }

    private void initiateConnection(String ipAddress, int port) {
        boolean connected = IntStream.rangeClosed(1, Constants.num_of_attempt_to_connect_toServer_before_giveup)
                .anyMatch(attempt -> tryToConnect(ipAddress, port, attempt));

        if (!connected) {
            staticPrinterNoNewLine("Giving up reconnection after too many attempts!");
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
                StaticPrinter.staticPrinter("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient exception: " + e + "\n");
            }
            staticPrinterNoNewLine("[#" + attempt + "] Retrying connection to Socket Server at port: '" + port + "' with IP: '" + ipAddress + "'");
            pause(Constants.seconds_between_reconnection);
            staticPrinterNoNewLine("\n");
            return false;
        }
    }

    private void handleDisconnection(Exception e) {
        StaticPrinter.staticPrinter("[ERROR] Connection to server lost! " + e);
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
                staticPrinterNoNewLine(".");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void createGame(String nickname, int numberOfPlayers) throws IOException {
        this.userNickname = nickname;
        outputStream.writeObject(new SocketClientMsgGameCreation(nickname, numberOfPlayers));
        finishSending();
    }

    @Override
    public void joinGame(String nickname, int gameId) throws IOException {
        this.userNickname = nickname;
        outputStream.writeObject(new SocketClientMsgJoinGame(nickname, gameId));
        finishSending();
    }

    @Override
    public void joinRandomly(String nickname) throws IOException {
        this.userNickname = nickname;
        outputStream.writeObject(new SocketClientMessageJoinFirstAvailableGame(nickname));
        finishSending();
    }

    @Override
    public void leave(String nickname, int gameId) throws IOException, NotBoundException {
        outputStream.writeObject(new SocketClientMsgLeaveGame(nickname, gameId));
        finishSending();
        this.userNickname = null;
    }

    @Override
    public void setAsReady() throws IOException, NotBoundException {
        outputStream.writeObject(new SocketClientMsgSetReady(userNickname));
        finishSending();
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws IOException, GameEndedException, NotBoundException {
        outputStream.writeObject(new SocketClientMsgPlaceStarterCard(orientation));
        finishSending();
    }

    @Override
    public void chooseCard(int cardIndex) throws IOException {
        outputStream.writeObject(new SocketClientMsgChooseCard(cardIndex));
        finishSending();
    }

    @Override
    public void placeCard(int xCoordinate, int yCoordinate, Orientation orientation) throws IOException {
        outputStream.writeObject(new SocketClientMsgPlaceCard(xCoordinate, yCoordinate, orientation));
        finishSending();
    }

    @Override
    public void drawCard(int cardIndex) throws IOException, GameEndedException {
        outputStream.writeObject(new SocketClientMsgDrawCard(cardIndex));
        finishSending();
    }

    @Override
    public void sendMessage(String receiver, Message message) throws IOException, NotBoundException {
        outputStream.writeObject(new SocketClientMsgSendMessage(receiver, message));
        finishSending();
    }

    @Override
    public void ping() {
        // Ping logic if needed
    }

    private void finishSending() throws IOException {
        outputStream.flush();
        outputStream.reset();
    }
}


