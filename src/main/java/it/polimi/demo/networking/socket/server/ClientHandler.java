package it.polimi.demo.networking.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;


import static it.polimi.demo.networking.PrintAsync.printAsync;

/**
 * ClientHandler Class<br>
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game<br>
 * by the Socket Network protocol
 */
public class ClientHandler extends Thread implements Serializable {
    /**
     * Socket associated with the Client
     */
    private final transient Socket clientSocket;
    /**
     * ObjectInputStream in
     */
    private transient ObjectInputStream ob_in;

    /**
     * ObjectOutputStream out
     */
    private transient ObjectOutputStream ob_out;

    /**
     * GameController associated with the game
     */
    private GameControllerInterface gameController;

    /**
     * The Listener of the ClientSocket for notifications
     */
    private GameListenersHandlerSocket gameListenersHandlerSocket;

    /**
     * Nickname of the SocketClient
     */
    private String nickname = null;

    private final BlockingQueue<SocketClientGenericMessage> processingQueue = new LinkedBlockingQueue<>();

    /**
     * Handle all the network requests performed by a specific ClientSocket
     *
     * @param socket the socket to the client
     * @throws IOException
     */
    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.ob_in = new ObjectInputStream(socket.getInputStream());
        this.ob_out = new ObjectOutputStream(socket.getOutputStream());

        gameListenersHandlerSocket = new GameListenersHandlerSocket(ob_out);
    }

    /**
     * Stop the thread
     */
    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public void run() {
        Thread logicThread = new Thread(this::handleGameLogic);
        logicThread.start();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SocketClientGenericMessage incomingMessage = (SocketClientGenericMessage) ob_in.readObject();
                    processingQueue.add(incomingMessage);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Connection to client lost, unable to continue communication.");
                    handleDisconnection();
                    return;
                }
            }
        } finally {
            logicThread.interrupt();
        }
    }

    private void handleDisconnection() {
        try {
            gameController.leave(gameListenersHandlerSocket, nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGameLogic() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                SocketClientGenericMessage message = processingQueue.take();
                if (message.isMessageForMainController()) {
                    gameController = message.perform(gameListenersHandlerSocket, MainController.getControllerInstance());
                    nickname = (gameController != null) ? message.getNick() : null;
                } else if (!message.isHeartbeat()) {
                    message.perform(gameController);
                }
            }
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

