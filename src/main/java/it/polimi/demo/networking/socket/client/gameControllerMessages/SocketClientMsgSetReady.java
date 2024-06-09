package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

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
