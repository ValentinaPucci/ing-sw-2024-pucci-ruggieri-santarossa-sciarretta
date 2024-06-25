package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgSuccessfulMove extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = -7118073750567528012L;
    private ModelView gamemodel;
    private Coordinate coord;

    public msgSuccessfulMove(ModelView gamemodel, Coordinate coord) {

        super("Successful Move");
        this.gamemodel = gamemodel;
        this.coord = coord;
    }


    //todo vale reimplement

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
       lis.successfulMove(gamemodel, coord);
    }
}
