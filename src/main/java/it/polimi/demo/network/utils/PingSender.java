package it.polimi.demo.network.utils;

import it.polimi.demo.view.dynamic.ClientInterface;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.NotBoundException;

/**
 * Sends a ping to the server to keep the connection alive.

 */
public class PingSender extends Thread implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ClientInterface server;

    /**
     * Creates a new PingSender.
     * @param server The server to which to send the ping.
     */
    public PingSender(ClientInterface server) {
        this.server = server;
    }

    /**
     * Sends a ping to the server every second.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                server.ping();
            } catch (IOException e) {
                StaticPrinter.staticPrinter("Connection to server lost! Impossible to send ping...");
                break;
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
