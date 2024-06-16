package it.polimi.demo.network.socket.server;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.demo.network.StaticPrinter.staticPrinter;

public class Server extends Thread implements Serializable {
    /**
     * Socket that represents the Server
     */
    private transient ServerSocket serverSocket;
    private int i = 0;
    /**
     * list of client handlers for each connection
     */
    private List<ClientConnection> handlers;

    /**
     * Constructor
     */
    public Server(){
        handlers = new ArrayList<>();
    }

    /**
     * Start method for the socket Server
     * @param server_port
     * @throws IOException
     */
    public void start(int server_port) throws IOException {
        try {
            serverSocket = new ServerSocket(server_port);
            this.start();
            staticPrinter("Server Socket READY");
        } catch (IOException e) {
            System.err.println("[ERROR] Initializing server socket: " + e.getMessage());
            throw e;
        }
    }

    /**
     * It accepts connections and starts a new ClientHandler for each one
     */
    public void run() {
        try {
            while (!Thread.interrupted()) {
                ClientConnection Client = new ClientConnection(serverSocket.accept());
                handlers.add(Client);
                handlers.get(handlers.size() - 1).start();
                staticPrinter("[SOCKET] New client connection accepted");
            }
        } catch (IOException e) { System.err.println("[ERROR] Accepting client connection: \n\t" + e.getMessage());}

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("[ERROR] Closing server socket: \n\t" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
