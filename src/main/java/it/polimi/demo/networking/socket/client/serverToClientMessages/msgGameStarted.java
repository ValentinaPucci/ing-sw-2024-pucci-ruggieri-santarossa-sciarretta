package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.listener.Listener;
import it.polimi.demo.model.ModelView;

import java.rmi.RemoteException;

/**
 * msgGameStarted class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that the game has started.
 */
public class msgGameStarted extends SocketServerGenericMessage {

    private ModelView model;

    /**
     * Empty constructor of the class.
     * Used when the game model is not provided.
     */
    public msgGameStarted() {

    }

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgGameStarted(ModelView model) {
        this.model = model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.gameStarted(model);
    }
}
