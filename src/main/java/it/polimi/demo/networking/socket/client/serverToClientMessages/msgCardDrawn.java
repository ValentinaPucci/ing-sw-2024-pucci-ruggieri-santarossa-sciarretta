package it.polimi.demo.networking.socket.client.serverToClientMessages;


import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;


public class msgCardDrawn extends SocketServerGenericMessage {
    private GameModelImmutable gameModel;
    private int index;

    public msgCardDrawn(GameModelImmutable gameModel, int index) {
        this.gameModel = gameModel;
        this.index = index;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.cardDrawn(gameModel, index);
    }

}
