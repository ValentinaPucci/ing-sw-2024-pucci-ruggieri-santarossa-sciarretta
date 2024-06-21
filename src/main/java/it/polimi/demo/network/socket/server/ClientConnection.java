package it.polimi.demo.network.socket.server;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;
import it.polimi.demo.network.GameControllerInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static it.polimi.demo.network.utils.StaticPrinter.staticPrinter;


public class ClientConnection extends Thread implements Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 'ObjectInputStream' used to receive messages from the client.
     */
    private transient ObjectInputStream inputStream;
    /**
     * 'ObjectOutputStream' used to send messages to the client.
     */
    private transient ObjectOutputStream outputStream;
    /**
     * 'GameControllerInterface' used to handle the game logic.
     */
    private GameControllerInterface controller;
    /**
     * Nickname of the client.
     */
    private String userNickname = null;
    /**
     * Queue of messages to process.
     */
    private final ConcurrentLinkedQueue<SocketClientGenericMessage> messageQueue = new ConcurrentLinkedQueue<>();
    /**
     * Flag to check if the client is running.
     */
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * Socket associated with the client.
     */
    private Socket socket;

    /**
     * Constructor for the class.
     *
     * @param socket the socket associated with the client
     */
    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

    /**
     * Method to start the client connection.
     * The buffer is needed to improve the socket connection performance. It wraps the object
     * streams with buffered streams. So the data is read and written in chunks, which is faster.
     */
    @Override
    public void run() {
        running.set(true);

        try {
            // Buffered streams for better performance
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            inputStream = new ObjectInputStream(bufferedInputStream);
            outputStream = new ObjectOutputStream(bufferedOutputStream);
            outputStream.flush();  // Ensure the header of ObjectOutputStream is sent

            GameListenersHandlerSocket gameListenerHandler = new GameListenersHandlerSocket(outputStream);

            Thread messageReaderThread = new Thread(this::readMessages);
            messageReaderThread.start();

            while (running.get()) {
                handleGameLogic(gameListenerHandler);
                Thread.sleep(10);  // Sleep briefly to prevent tight loop
            }

            messageReaderThread.join();
        } catch (IOException | InterruptedException e) {
            disconnectClient();
        }
    }

    /**
     * Method to read messages.
     */
    private void readMessages() {
        try {
            while (running.get()) {
                SocketClientGenericMessage message = (SocketClientGenericMessage) inputStream.readObject();
                messageQueue.add(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            disconnectClient();
        }
    }

    /**
     * Method used to handle the game logic .
     * @param gameListenerHandler
     */
    private void handleGameLogic(GameListenersHandlerSocket gameListenerHandler) {
        SocketClientGenericMessage message = messageQueue.poll();
        if (message != null) {
            try {
                if (message.isMainControllerTarget()) {
                    controller = message.performOnMainController(gameListenerHandler, MainController.getControllerInstance());
                    userNickname = controller != null ? message.getUserNickname() : null;
                } else if (!message.isHeartbeatMessage()) {
                    message.performOnGameController(controller);
                }
            } catch (RemoteException | GameEndedException e) {
                staticPrinter("Error handling game logic: " + e.getMessage());
            }
        }
    }

    /**
     * Method to disconnect the client.
     */
    private void disconnectClient() {
        staticPrinter("ClientSocket disconnected due to communication failure");
        try {
            if (controller != null) {
                controller.leave(new GameListenersHandlerSocket(outputStream), userNickname);
            }
        } catch (RemoteException e) {
            staticPrinter("Error during disconnection: " + e.getMessage());
        }
    }
}
