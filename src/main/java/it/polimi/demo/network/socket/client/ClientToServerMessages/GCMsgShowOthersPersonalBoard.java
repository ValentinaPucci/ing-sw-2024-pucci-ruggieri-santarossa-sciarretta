package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
public class GCMsgShowOthersPersonalBoard extends GCMsg implements Serializable {
    @Serial
    private static final long serialVersionUID = 7272108078804819811L;
    private int index;
    private String nickname;

    public GCMsgShowOthersPersonalBoard(String nickname, int index) {
        this.index = index;
        this.nickname = nickname;
    }

    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.showOthersPersonalBoard(nickname, index);
    }
}
