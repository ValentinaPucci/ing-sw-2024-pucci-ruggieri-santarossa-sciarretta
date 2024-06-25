package it.polimi.demo.network.socket.client.MsgForGameController;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class to send the draw card message to the server.
 */
public class SocketClientMsgDrawCard extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 7272108078804819811L;
    private int index;

    /**
     * Constructor of the class.
     * @param index
     */
    public SocketClientMsgDrawCard(int index) {
         this.index = index;
         this.setMainControllerTarget(false);
    }

    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to perform drawCard on the game controller.
     * @param gameController the game controller interface
     * @throws RemoteException
     * @throws GameEndedException
     */
    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.drawCard(gameController.getCurrentPlayer().getNickname(), index);
    }
}
