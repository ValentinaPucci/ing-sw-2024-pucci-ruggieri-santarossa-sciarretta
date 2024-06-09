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
        this.nick = nick;
        this.isMessageForMainController = true;
    }


    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinRandomly(lis, nick);
    }


    @Override
    public void perform(GameControllerInterface mainController) throws RemoteException {

    }
}
