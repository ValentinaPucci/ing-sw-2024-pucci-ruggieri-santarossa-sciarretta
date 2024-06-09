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

public class ClientHandler extends Thread implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  input
     */
    private transient ObjectInputStream ob_in;
    /**
     * output
     */
    private transient ObjectOutputStream ob_out;
    private GameControllerInterface gameController;
    private GameListenersHandlerSocket gameListenersHandlerSocket;
    private String nickname = null;
    private final BlockingQueue<SocketClientGenericMessage> processingQueue = new LinkedBlockingQueue<>();

    public ClientHandler(Socket soc) throws IOException {
        this.ob_in = new ObjectInputStream(soc.getInputStream());
        this.ob_out = new ObjectOutputStream(soc.getOutputStream());
        this.gameListenersHandlerSocket = new GameListenersHandlerSocket(ob_out);
    }

    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public void run() {
        var gameLogicThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    processGameLogic();
                }
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException ignored) {
            }
        });
        gameLogicThread.start();

        try {
            while (!Thread.currentThread().isInterrupted()) {
                processClientMessage();
            }
        } finally {
            gameLogicThread.interrupt();
        }
    }

    private void processClientMessage() {
        try {
            var temp = (SocketClientGenericMessage) ob_in.readObject();
            processingQueue.add(temp);
        } catch (IOException | ClassNotFoundException e) {
            handleClientDisconnect();
        }
    }

    private void processGameLogic() throws RemoteException, GameEndedException, InterruptedException {
        var temp = processingQueue.take();
        if (temp.isMainControllerTarget()) {
            gameController = temp.performOnMainController(gameListenersHandlerSocket, MainController.getControllerInstance());
            nickname = gameController != null ? temp.getUserNickname() : null;
        } else if (!temp.isHeartbeatMessage()) {
            temp.performOnGameController(gameController);
        }
    }

    private void handleClientDisconnect() {
        printAsync("ClientSocket dies because cannot communicate no more with the client");
        try {
            if (gameController != null) {
                gameController.leave(gameListenersHandlerSocket, nickname);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}


