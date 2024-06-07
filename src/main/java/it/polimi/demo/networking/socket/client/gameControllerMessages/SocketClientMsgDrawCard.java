package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class SocketClientMsgDrawCard extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 7272108078804819811L;
    private int index;

    public SocketClientMsgDrawCard(int index) {
         this.index = index;
         this.isMessageForMainController = false;
    }


    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }


    @Override
    public void perform(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.drawCard(gameController.getCurrentPlayer().getNickname(), index);
    }
}
