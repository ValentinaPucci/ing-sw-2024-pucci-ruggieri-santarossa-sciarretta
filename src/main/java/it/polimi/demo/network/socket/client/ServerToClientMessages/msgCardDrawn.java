package it.polimi.demo.network.socket.client.ServerToClientMessages;


import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;


public class msgCardDrawn extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = 1145544034549465586L;
    private ModelView gameModel;
    private int index;

    public msgCardDrawn(ModelView gameModel, int index) {
        super("Card drawn");
        this.gameModel = gameModel;
        this.index = index;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.cardDrawn(gameModel, index);
    }

}
