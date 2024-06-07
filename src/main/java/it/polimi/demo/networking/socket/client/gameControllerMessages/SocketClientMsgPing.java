package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.rmi.RemoteException;

public class SocketClientMsgPing extends SocketClientGenericMessage {

    @Serial
    private static final long serialVersionUID = 6780458660694095047L;

    public SocketClientMsgPing(String nick) {
        this.nick = nick;
        this.isMessageForMainController = false;
        this.isHeartbeat = true;
    }

    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    @Override
    public void perform(GameControllerInterface gameController) throws RemoteException, GameEndedException {

    }
}
