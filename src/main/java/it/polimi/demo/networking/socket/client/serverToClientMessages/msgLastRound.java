package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.rmi.RemoteException;

/**
 * msgLastRound class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that it is the last circle of the game.
 */
public class msgLastRound extends SocketServerGenericMessage {
    private ModelView gamemodel;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgLastRound(ModelView gamemodel) {
        this.gamemodel = gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
       lis.lastRound(gamemodel);
    }
}
