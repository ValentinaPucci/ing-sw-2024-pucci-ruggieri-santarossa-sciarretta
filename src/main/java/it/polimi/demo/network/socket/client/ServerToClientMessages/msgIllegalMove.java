package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgIllegalMove extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = 5869414913637943251L;
    private ModelView gamemodel;

    public msgIllegalMove(ModelView gamemodel) {
        super("Illegal Move");
        this.gamemodel = gamemodel;
    }
    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        //logMessage();
        lis.illegalMove(gamemodel);
    }
}