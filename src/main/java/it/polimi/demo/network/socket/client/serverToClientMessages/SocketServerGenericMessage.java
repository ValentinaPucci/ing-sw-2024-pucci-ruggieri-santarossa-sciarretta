package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;

import java.io.IOException;
import java.io.Serializable;

public abstract class SocketServerGenericMessage implements Serializable {

    public abstract void perform(Listener lis) throws IOException, InterruptedException;

}
