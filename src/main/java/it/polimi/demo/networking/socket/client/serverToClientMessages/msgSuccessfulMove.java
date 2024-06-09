package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgSuccessfulMove extends SocketServerGenericMessage{
    @Serial
    private static final long serialVersionUID = -7118073750567528012L;
    private ModelView gamemodel;

    public msgSuccessfulMove(ModelView gamemodel) {

        super("Successful Move");
        this.gamemodel = gamemodel;
    }


    //todo vale reimplement
    @Override
    public void perform(Listener lis) throws RemoteException {
//        lis.successfulMove(gamemodel);
    }
}
