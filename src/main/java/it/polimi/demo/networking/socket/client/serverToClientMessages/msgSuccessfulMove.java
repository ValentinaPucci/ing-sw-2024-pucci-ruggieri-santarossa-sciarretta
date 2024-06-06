package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.listener.Listener;
import it.polimi.demo.model.ModelView;

import java.rmi.RemoteException;

public class msgSuccessfulMove extends SocketServerGenericMessage{
    private ModelView gamemodel;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgSuccessfulMove(ModelView gamemodel) {
        this.gamemodel = gamemodel;
    }


    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.successfulMove(gamemodel);
    }
}
