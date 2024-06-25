package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

/**
 * This class represents the message sent by the server to the client to notify that a card has been chosen.
 */
public class msgCardChosen extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = 7914302356958980212L;
    private ModelView gameModel;
    private int which_card;

    /**
     * Constructor.
     * @param gameModel
     * @param which_card
     */
    public msgCardChosen(ModelView gameModel, int which_card) {
        super("Card chosen");
        this.gameModel = gameModel;
        this.which_card = which_card;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.cardChosen(gameModel, which_card);
    }

}


