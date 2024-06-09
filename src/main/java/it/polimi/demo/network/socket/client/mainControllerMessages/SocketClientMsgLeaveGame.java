package it.polimi.demo.network.socket.client.mainControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.rmi.RemoteException;

public class SocketClientMsgLeaveGame extends SocketClientGenericMessage {

    @Serial
    private static final long serialVersionUID = 6214430342463902214L;
    int game_id;

    public SocketClientMsgLeaveGame(String nick, int game_id) {
        this.game_id = game_id;
        this.nick = nick;
        this.isMessageForMainController = true;
    }

    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        mainController.leaveGame(lis, nick, game_id);
        return null;
    }


    @Override
    public void perform(GameControllerInterface mainController) throws RemoteException {

    }
}