package it.polimi.demo.networking.socket.client.serverToClientMessages;
import it.polimi.demo.listener.Listener;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;

import java.rmi.RemoteException;


public class msgStarterCardPlaced extends SocketServerGenericMessage{
    private ModelView gameModel;
    private Orientation orientation;

    public msgStarterCardPlaced(ModelView gameModel, Orientation orientation) {
        this.gameModel = gameModel;
        this.orientation = orientation;
    }

    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.starterCardPlaced(gameModel, orientation, gameModel.getCurrentPlayerNickname());
    }

}
