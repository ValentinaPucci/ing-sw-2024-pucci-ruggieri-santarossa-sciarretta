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

    public SocketClientMsgSetReady(String nick) {
        this.nick = nick;
        this.isMessageForMainController = false;
    }


    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }


    @Override
    public void perform(GameControllerInterface gameController) throws RemoteException {
        gameController.playerIsReadyToStart(this.nick);
    }
}
