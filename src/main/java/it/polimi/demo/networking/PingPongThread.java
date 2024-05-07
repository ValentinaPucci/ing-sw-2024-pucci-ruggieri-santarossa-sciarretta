package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;

import java.rmi.RemoteException;

/**
 * This thread is used by the server to check if the client is still connected.
 * It is started when the client registers.
 * The server sends a ping to the client and waits for a pong. If the pong is not received within a certain time,
 * which is defined in Constants, the client is considered disconnected.
 */
public class PingPongThread extends Thread {
    /**
     * This boolean is used in the ping-pong mechanism to check if the client is still connected to the server.
     * It is set to false before the ping is sent.
     * If the server receives a pong, this should be set as true using {@link #pongReceived()}.
     */
    private boolean pong;
    /**
     * Instance of the server that started the thread.
     */
    private final ServerImpl server;

    /**
     * Constructor of the thread.
     * @param server the server that will start the ping-pong mechanism
     */
    public PingPongThread(ServerImpl server) {
        super("PingPongThread");

        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            pong = false;

            try {
                server.client.ping();
            } catch (RemoteException e) {
                break;
            }

            try {
                sleep(DefaultValues.pingpongTimeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!pong) {
                break;
            }
        }

        this.handlePlayerDisconnection();
    }

    //TODO: IMPLEMENT
    private void handlePlayerDisconnection() {
        // Checks if the client is already in a game
    }

    /**
     * This method should be called by the server where a pong is received from the client.
     */
    public void pongReceived() {
        this.pong = true;
    }
}