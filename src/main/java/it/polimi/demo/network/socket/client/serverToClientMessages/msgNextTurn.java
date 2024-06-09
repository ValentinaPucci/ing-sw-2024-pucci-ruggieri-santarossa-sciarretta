package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;


public class msgNextTurn extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = -8511364392512728450L;
    private ModelView gamemodel;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgNextTurn(ModelView gamemodel) {
        super("Next Turn");
        this.gamemodel = gamemodel;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.nextTurn(gamemodel);
    }
}
