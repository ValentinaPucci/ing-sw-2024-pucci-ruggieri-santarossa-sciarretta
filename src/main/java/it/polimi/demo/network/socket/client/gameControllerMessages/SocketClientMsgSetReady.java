package it.polimi.demo.network.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class that represents the message sent by the client to the game controller to notify that the player is ready to start the game.

 */
public class SocketClientMsgSetReady extends SocketClientGenericMessage implements Serializable {


    @Serial
    private static final long serialVersionUID = 3105139738605427270L;

    /**
     * Constructor of the class.
     */
    public SocketClientMsgSetReady(String nick) {
        this.setUserNickname(nick);
        this.setMainControllerTarget(false);
    }


    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }
    /**
     * Method to perform playerIsReadyToStart on the game controller.
     * @param gameController the game controller interface
     * @throws RemoteException
     */
    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException {
        gameController.playerIsReadyToStart(this.getUserNickname());
    }
}
