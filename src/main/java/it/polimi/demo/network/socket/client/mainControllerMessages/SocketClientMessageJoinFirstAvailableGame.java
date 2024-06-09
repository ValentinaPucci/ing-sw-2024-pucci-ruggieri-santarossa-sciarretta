package it.polimi.demo.networking.socket.client.mainControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

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
        return mainController.joinFirstAvailableGame(lis, this.getUserNickname());
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
