package it.polimi.demo.network.socket.client.mainControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;


public class SocketClientMessageJoinFirstAvailableGame extends SocketClientGenericMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -5403664201604025015L;

    public SocketClientMessageJoinFirstAvailableGame(String nick) {
        this.setUserNickname(nick);
        this.setMainControllerTarget(true);
    }


    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinRandomly(lis, this.getUserNickname());
    }

    /**
     * Just for implementation purposes
     * @param mainController the game controller interface
     * @throws RemoteException
     */
    @Override
    public void performOnGameController(GameControllerInterface mainController) throws RemoteException {

    }
}
