package it.polimi.demo.network.socket.client.serverToClientMessages;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;


public class msgStarterCardPlaced extends SocketServerGenericMessage{
    @Serial
    private static final long serialVersionUID = -4325572341944203763L;
    private ModelView gameModel;
    private Orientation orientation;

    public msgStarterCardPlaced(ModelView gameModel, Orientation orientation) {
        this.gameModel = gameModel;
        this.orientation = orientation;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.starterCardPlaced(gameModel, orientation, gameModel.getCurrentPlayerNickname());
    }

}
