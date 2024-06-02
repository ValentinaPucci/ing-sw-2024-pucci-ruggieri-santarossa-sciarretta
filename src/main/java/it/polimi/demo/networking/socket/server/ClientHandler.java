package it.polimi.demo.networking.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.demo.controller.GameController;
import it.polimi.demo.controller.MainController;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;


import static it.polimi.demo.networking.PrintAsync.printAsync;

/**
 * ClientHandler Class<br>
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game<br>
 * by the Socket Network protocol
 */
public class ClientHandler extends Thread implements Serializable{
    /**
     * Socket associated with the Client
     */
    private final transient Socket clientSocket;
    /**
     * ObjectInputStream in
     */
    private transient ObjectInputStream in;

    /**
     * ObjectOutputStream out
     */
    private transient ObjectOutputStream out;

    /**
     * GameController associated with the game
     */
    private GameControllerInterface gameController;

    /**
     * The GameListener of the ClientSocket for notifications
     */
    private GameListenersHandlerSocket gameListenersHandlerSocket;

    /**
     * Nickname of the SocketClient
     */
    private String nickname = null;

    private final transient BlockingQueue<SocketClientGenericMessage> processingQueue = new LinkedBlockingQueue<>();

    /**
     * Handle all the network requests performed by a specific ClientSocket
     *
     * @param soc the socket to the client
     * @throws IOException
     */
    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        gameListenersHandlerSocket = new GameListenersHandlerSocket(out);
    }

    /**
     * Stop the thread
     */
    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public  void run() {
        System.out.println("Run ");
        var th = new Thread(this::runGameLogic);
        th.start();

        try {
            SocketClientGenericMessage temp;
            while (!this.isInterrupted()) {
                try {
                    temp = (SocketClientGenericMessage) in.readObject();
                    try {
                        //it's a heartbeat message I handle it as a "special message"
                        if (temp.isHeartbeat() && !temp.isMessageForMainController()) {
                            if (gameController != null) {
                                gameController.addPing(temp.getNick(), gameListenersHandlerSocket);
                            }
                        } else {
                            processingQueue.add(temp);
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    // Error here in socket, cannot communicate with client anymore --> rmi connection lost
                    printAsync("ClientSocket dies because cannot communicate no more with the client");
                    return;
                }
            }
        } finally {
            th.interrupt();
        }
    }

    private void runGameLogic() {
        SocketClientGenericMessage temp;
        try {
            while (!this.isInterrupted()) {
                    temp = processingQueue.take();
                    if (temp.isMessageForMainController()) {
                        gameController = temp.execute(gameListenersHandlerSocket, MainController.getControllerInstance());
                        nickname = gameController != null ? temp.getNick() : null;
                    } else if (!temp.isHeartbeat()) {
                            temp.execute(gameController);
                    }
                }
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException ignored) {

        }
    }
}

