package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.listener.Listener;
import it.polimi.demo.model.ModelView;

import java.rmi.RemoteException;

/**
 * msgPlayerLeft class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has left the game.
 */
public class msgPlayerLeft extends SocketServerGenericMessage{
    private ModelView gamemodel;
    private String nick;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     * @param nick the nickname of the player who left
     */
    public msgPlayerLeft(ModelView gamemodel, String nick) {
        this.gamemodel = gamemodel;
        this.nick=nick;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.playerLeft(gamemodel,nick);
    }
}
