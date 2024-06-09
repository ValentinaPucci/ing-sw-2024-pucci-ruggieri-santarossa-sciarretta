package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;

import java.io.IOException;
import java.io.Serializable;

public abstract class SocketServerGenericMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String messageContent;

    public SocketServerGenericMessage(String messageContent) {
        this.messageContent = messageContent;
    }

    public abstract void perform(Listener lis) throws IOException, InterruptedException;

    public void logMessage() {
        System.out.println("Processing message: " + messageContent);
    }
}
