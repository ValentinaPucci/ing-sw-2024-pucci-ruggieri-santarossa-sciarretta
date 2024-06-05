package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMessageHeartbeat extends SocketClientGenericMessage {

    public SocketClientMessageHeartbeat(String nick) {
        this.nick = nick;
        this.isMessageForMainController = false;
        this.isHeartbeat = true;
    }

    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {

    }
}
