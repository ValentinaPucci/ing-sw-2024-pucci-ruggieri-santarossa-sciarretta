package it.polimi.demo.networking.socket.client.serverToClientMessages;


import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;


public class msgCardDrawn extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 1145544034549465586L;
    private ModelView gameModel;
    private int index;

    public msgCardDrawn(ModelView gameModel, int index) {
        super("Card drawn");
        this.gameModel = gameModel;
        this.index = index;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.cardDrawn(gameModel, index);
    }

}
