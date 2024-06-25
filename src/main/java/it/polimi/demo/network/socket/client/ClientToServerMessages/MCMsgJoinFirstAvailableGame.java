package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class represents the message sent by the client to the main controller to join the first available game
 */

public class MCMsgJoinFirstAvailableGame extends MCMsg implements Serializable {

    @Serial
    private static final long serialVersionUID = -5403664201604025015L;

    public MCMsgJoinFirstAvailableGame(String nick) {
        this.setUserNickname(nick);
    }

    /**
     * used to perform the action on the main controller
     * @param lis the game observer
     * @param mainController the main controller interface
     * @return
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinRandomly(lis, this.getUserNickname());
    }
}
