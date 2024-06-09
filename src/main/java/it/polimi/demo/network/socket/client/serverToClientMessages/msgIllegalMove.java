package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgIllegalMove extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 5869414913637943251L;
    private ModelView gamemodel;

    public msgIllegalMove(ModelView gamemodel) {
        super("Illegal Move");
        this.gamemodel = gamemodel;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        //logMessage();
        lis.illegalMove(gamemodel);
    }
}