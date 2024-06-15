package it.polimi.demo.network.socket.server;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;
import it.polimi.demo.network.interfaces.GameControllerInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static it.polimi.demo.network.StaticPrinter.staticPrinter;
import static it.polimi.demo.network.StaticPrinter.staticPrinterNoNewLine;


public class ClientConnection extends Thread implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient ObjectInputStream inputStream;
    private transient ObjectOutputStream outputStream;
    private GameControllerInterface controller;
    private String userNickname = null;
    private final ConcurrentLinkedQueue<SocketClientGenericMessage> messageQueue = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(false);

    private Socket socket;

    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

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
