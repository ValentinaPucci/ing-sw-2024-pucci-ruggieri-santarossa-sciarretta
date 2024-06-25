package it.polimi.demo.network.socket.server;

import it.polimi.demo.network.socket.ClientConnection;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static it.polimi.demo.network.utils.StaticPrinter.staticPrinter;

/**
 * SocketServer class that represents the server socket
 */
public class SocketServer extends Thread implements Serializable {

    @Serial
    private static final long serialVersionUID = -3450892858052318586L;

    /**
     * Socket that represents the SocketServer
     */
    private final transient java.net.ServerSocket serverSocket;

    /**
     * List of client handlers for each connection
     */
    private final List<ClientConnection> handlers;

    /**
     * Constructor that starts the server with the specified port
     * @param serverPort the port on which the server will listen
     */
    public SocketServer(int serverPort) {
        handlers = new ArrayList<>();
        try {
            serverSocket = new java.net.ServerSocket(serverPort);
            staticPrinter("SocketServer Socket READY");

            // Start listening for client connections
            this.start();
        } catch (IOException e) {
            System.err.println("[ERROR] Initializing server socket: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Run method that accepts connections and starts a new ClientHandler for each one
     */
    public void run() {
        try {
            Consumer<ClientConnection> handleClient = client -> {
                handlers.add(client);
                client.start();
                staticPrinter(" New client  SOCKET connection accepted");
            };

            while (!Thread.interrupted()) {
                ClientConnection client = acceptClientConnection();
                if (client != null) {
                    handleClient.accept(client);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in SocketServer run method: " + e.getMessage());
        } finally {
            closeServerSocket();
        }
    }

    /**
     * Accepts a client connection
     * @return a new ClientConnection or null if an error occurs
     */
    private ClientConnection acceptClientConnection() {
        try {
            return new ClientConnection(serverSocket.accept());
        } catch (IOException e) {
            System.err.println("Error accepting client connection: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the server socket
     */
    private void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

