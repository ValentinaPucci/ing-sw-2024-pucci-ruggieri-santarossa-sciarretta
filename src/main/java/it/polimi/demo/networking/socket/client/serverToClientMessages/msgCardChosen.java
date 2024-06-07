package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.rmi.RemoteException;

public class msgCardChosen extends  SocketServerGenericMessage{
    private ModelView gameModel;
    private int which_card;

    public msgCardChosen(ModelView gameModel, int which_card) {
        this.gameModel = gameModel;
        this.which_card = which_card;
    }

    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.cardChosen(gameModel, which_card);
    }
}

