package it.polimi.demo.network.socket.client.mainControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;


public class SocketClientMsgGameCreation extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1564655861858986913L;
    int num_players;

    public SocketClientMsgGameCreation(String nick, int num_players) {
        this.nick = nick;
        this.num_players = num_players;
        this.isMessageForMainController = true;
    }

    @Override
    public GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.createGame(lis, nick, num_players);
    }

    @Override
    public void perform(GameControllerInterface mainController) throws RemoteException {

    }
}
