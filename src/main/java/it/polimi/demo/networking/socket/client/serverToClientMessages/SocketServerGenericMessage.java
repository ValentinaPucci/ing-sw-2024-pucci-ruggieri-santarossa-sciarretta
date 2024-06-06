package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;

import java.io.IOException;
import java.io.Serializable;

/**
 * SocketServerGenericMessage class.
 * An abstract class that represents a generic message to be sent from the server to the client.
 */
public abstract class SocketServerGenericMessage implements Serializable {

    /**
     * Executes the corresponding action for the message.
     * @param lis the game observer
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    public abstract void execute(Listener lis) throws IOException, InterruptedException;

}
