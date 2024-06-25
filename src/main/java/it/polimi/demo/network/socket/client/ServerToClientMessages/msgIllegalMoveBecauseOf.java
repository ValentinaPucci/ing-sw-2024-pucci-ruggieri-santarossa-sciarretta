package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgIllegalMoveBecauseOf extends S2CGenericMessage {

    @Serial
    private static final long serialVersionUID = 8555800278098017630L;
    private ModelView gamemodel;
    private String message;


    public msgIllegalMoveBecauseOf(ModelView gamemodel, String message) {
        super("Illegal Move Because Of");
        this.gamemodel = gamemodel;
        this.message = message;
    }


    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.illegalMoveBecauseOf(gamemodel, message);
    }
}
