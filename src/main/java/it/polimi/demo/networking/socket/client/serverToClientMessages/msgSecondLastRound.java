package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

/**
 * msgLastRound class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that it is the last circle of the game.
 */
public class msgSecondLastRound extends SocketServerGenericMessage {
    private GameModelImmutable gamemodel;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgSecondLastRound(GameModelImmutable gamemodel) {
        this.gamemodel = gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.lastRound(gamemodel);
    }
}