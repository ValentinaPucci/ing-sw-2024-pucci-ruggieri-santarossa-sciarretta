package it.polimi.demo.network.socket.client.gameControllerMessages;

import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;
import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMsgShowOthersPersonalBoard extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 7272108078804819811L;
    private int index;
    private String nickname;

    public SocketClientMsgShowOthersPersonalBoard(String nickname, int index) {
        this.index = index;
        this.nickname = nickname;
        this.setMainControllerTarget(false);
    }


    @Override
    public GameControllerInterface performOnMainController(Listener observer, MainControllerInterface mainCtrlInterface) throws RemoteException {
        return null;
    }

    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.showOthersPersonalBoard(nickname, index);
    }
}
