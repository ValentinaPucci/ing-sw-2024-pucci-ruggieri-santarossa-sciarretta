package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.observer.Listener;

import java.rmi.RemoteException;

/**
 * msgPlayerDisconnected class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has been disconnected from the game.
 */
public class msgPlayerDisconnected extends SocketServerGenericMessage {
    private String nick;
    private ModelView gameModel;

    /**
     * Constructor of the class.
     * @param gameModel the immutable game model
     * @param nick the nickname of the disconnected player
     */
    public msgPlayerDisconnected(ModelView gameModel, String nick) {
        this.nick = nick;
        this.gameModel=gameModel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game observer
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.playerDisconnected(gameModel,nick);
    }
}
