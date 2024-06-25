package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class represents the message sent by the client to the main controller to create a new game.
 */
public class MCMsgGameCreation extends MCMsg implements Serializable {
    @Serial
    private static final long serialVersionUID = 1564655861858986913L;
    int num_players;

    public MCMsgGameCreation(String nick, int num_players) {
        this.setUserNickname(nick);
        this.num_players = num_players;
    }

    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.createGame(lis, this.getUserNickname(), num_players);
    }
}
