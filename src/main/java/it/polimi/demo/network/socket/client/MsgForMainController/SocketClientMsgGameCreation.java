package it.polimi.demo.network.socket.client.MsgForMainController;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class represents the message sent by the client to the main controller to create a new game.
 */
public class SocketClientMsgGameCreation extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1564655861858986913L;
    int num_players;

    public SocketClientMsgGameCreation(String nick, int num_players) {
        this.setUserNickname(nick);
        this.setMainControllerTarget(true);
        this.num_players = num_players;}

    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.createGame(lis, this.getUserNickname(), num_players);
    }

    @Override
    public void performOnGameController(GameControllerInterface mainController) throws RemoteException {
        // Do nothing
    }
}
