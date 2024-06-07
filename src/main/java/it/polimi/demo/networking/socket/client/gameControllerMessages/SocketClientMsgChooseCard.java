package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class represents the message sent by the client to the server to choose a card from the hand.
 */
public class SocketClientMsgChooseCard extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 5796017918043405008L;
    private int which_card;

    public SocketClientMsgChooseCard(int which_card) {
        this.which_card = which_card;
        this.isMessageForMainController = false;
    }


    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    @Override
    public void perform(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.chooseCardFromHand(gameController.getCurrentPlayer().getNickname(), which_card);
    }
}
