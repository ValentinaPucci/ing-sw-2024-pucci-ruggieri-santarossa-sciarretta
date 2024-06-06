package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.observer.Listener;

import java.rmi.RemoteException;

public class msgIllegalMoveBecauseOf extends SocketServerGenericMessage {

    private ModelView gamemodel;
    private String message;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgIllegalMoveBecauseOf(ModelView gamemodel, String message) {
        this.gamemodel = gamemodel;
        this.message = message;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game observer
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.illegalMoveBecauseOf(gamemodel, message);
    }
}
