package it.polimi.demo.networking.socket.client.serverToClientMessages;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;


public class msgStarterCardPlaced extends SocketServerGenericMessage{
    private GameModelImmutable gameModel;
    private Orientation orientation;

    public msgStarterCardPlaced(GameModelImmutable gameModel, Orientation orientation) {
        this.gameModel = gameModel;
        this.orientation = orientation;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.starterCardPlaced(gameModel, orientation);
    }

}
