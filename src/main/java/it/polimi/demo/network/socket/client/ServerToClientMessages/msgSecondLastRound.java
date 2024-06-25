package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgSecondLastRound extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = -7023069901767637115L;
    private ModelView gamemodel;

    public msgSecondLastRound(ModelView gamemodel) {

        super("Second Last Round");
        this.gamemodel = gamemodel;
    }


    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.lastRound(gamemodel);
    }
}