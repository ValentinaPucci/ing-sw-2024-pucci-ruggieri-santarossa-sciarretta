package it.polimi.demo.network.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

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

    /**
     * Constructor of the class.
     * @param which_card the index of the card to choose
     */
    public SocketClientMsgChooseCard(int which_card) {
        this.which_card = which_card;
        this.isMessageForMainController = false;
    }

    /**
     * Method to perform the corresponding action of the message.
     * @param lis the game observer
     * @param mainController the main controller interface
     * @return
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to call chooseCardFromHand on the game controller.
     * @param gameController the game controller interface
     * @throws RemoteException
     * @throws GameEndedException
     */
    @Override
    public void perform(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.chooseCardFromHand(gameController.getCurrentPlayer().getNickname(), which_card);
    }
}
