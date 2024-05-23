package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;

import java.rmi.RemoteException;

public class msgCardChosen extends  SocketServerGenericMessage{
    private GameModelImmutable gameModel;
    private int which_card;

    public msgCardChosen(GameModelImmutable gameModel, int which_card) {
        this.gameModel = gameModel;
        this.which_card = which_card;
    }

    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.cardChosen(gameModel, which_card);
    }
}

