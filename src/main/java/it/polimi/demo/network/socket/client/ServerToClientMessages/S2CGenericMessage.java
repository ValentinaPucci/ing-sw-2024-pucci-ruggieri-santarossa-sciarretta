package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Abstract class for the messages sent from the server to the client to notify actions.

 */

public abstract class S2CGenericMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String messageContent;

    /**
     * Constructor, sets the message content.
     * @param messageContent
     */

    public S2CGenericMessage(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    public abstract void perform(Listener lis) throws IOException, InterruptedException;

    /**
     * Method to log the message.
     */
    public void logMessage() {
        System.out.println("Processing message: " + messageContent);
    }
}
