package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgCardChosen extends  SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 7914302356958980212L;
    private ModelView gameModel;
    private int which_card;

    public msgCardChosen(ModelView gameModel, int which_card) {
        super("Card chosen");
        this.gameModel = gameModel;
        this.which_card = which_card;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.cardChosen(gameModel, which_card);
    }

}


