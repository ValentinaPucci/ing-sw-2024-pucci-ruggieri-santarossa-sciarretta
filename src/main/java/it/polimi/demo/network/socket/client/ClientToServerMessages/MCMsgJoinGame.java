package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class MCMsgJoinGame extends MCMsg implements Serializable {

    @Serial
    private static final long serialVersionUID = -6436517584047451724L;
    int game_id;

    /**
     * Constructs a message to join a specific game.
     *
     * @param nick the nickname of the player
     * @param game_id the ID of the game to join
     */
    public MCMsgJoinGame(String nick, int game_id) {
        this.game_id = game_id;
        this.setUserNickname(nick);
    }

    /**
     * Executes the join game request on the main controller.
     *
     * @param lis the game observer
     * @param mainController the main controller interface
     * @return the game controller interface
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinGame(lis, this.getUserNickname(), game_id);
    }

}
