package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;

import java.io.Serial;
import java.rmi.RemoteException;
//TODO: Check if it works

/**
 * This class represents the message sent by the client to the main controller to leave a game.
 */
public class MCMsgLeaveGame extends MCMsg {

    @Serial
    private static final long serialVersionUID = 6214430342463902214L;
    int game_id;

    public MCMsgLeaveGame(String nick, int game_id) {
        this.game_id = game_id;
        this.setUserNickname(nick);
    }

    /**
     * This method used to call on the main controller the leaveGame method
     * @param lis the listener
     * @param mainController the main controller interface
     * @return the game controller interface
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        mainController.leaveGame(lis, getUserNickname(), game_id);
        return mainController.getGameController(game_id);
    }
}