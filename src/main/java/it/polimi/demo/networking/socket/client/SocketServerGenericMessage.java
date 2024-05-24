package it.polimi.demo.networking.socket.client;


import it.polimi.demo.listener.GameListener;


import java.io.IOException;
import java.io.Serializable;

public abstract class SocketServerGenericMessage implements Serializable {

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    public abstract void execute(GameListener lis) throws IOException, InterruptedException;

}
