package it.polimi.demo.network.socket.client.MsgForGameController;

import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGameControllerMex;
import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
public class SocketClientMsgShowOthersPersonalBoard extends SocketClientGameControllerMex implements Serializable {
    @Serial
    private static final long serialVersionUID = 7272108078804819811L;
    private int index;
    private String nickname;

    public SocketClientMsgShowOthersPersonalBoard(String nickname, int index) {
        this.index = index;
        this.nickname = nickname;
    }

    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.showOthersPersonalBoard(nickname, index);
    }
}
