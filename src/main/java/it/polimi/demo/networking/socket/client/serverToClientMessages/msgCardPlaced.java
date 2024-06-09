package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;


public class msgCardPlaced extends SocketServerGenericMessage {

    @Serial
    private static final long serialVersionUID = 1646902891110952903L;
    private ModelView gamemodel;
    private int x;
    private int y;
    private Orientation orientation;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgCardPlaced(ModelView gamemodel, int x, int y, Orientation orientation) {
        super("Card placed");
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.gamemodel = gamemodel;
    }


    @Override
    public void perform(Listener lis) throws RemoteException {

        lis.cardPlaced(gamemodel, x, y, orientation);
    }
}
