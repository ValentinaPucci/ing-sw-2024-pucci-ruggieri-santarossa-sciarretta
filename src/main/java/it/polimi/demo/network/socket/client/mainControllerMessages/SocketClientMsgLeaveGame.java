package it.polimi.demo.network.socket.client.mainControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.rmi.RemoteException;
//TODO: Check if it works

public class SocketClientMsgLeaveGame extends SocketClientGenericMessage {

    @Serial
    private static final long serialVersionUID = 6214430342463902214L;
    int game_id;

    public SocketClientMsgLeaveGame(String nick, int game_id) {
        this.game_id = game_id;
        this.setUserNickname(nick);
        this.setMainControllerTarget(true);
    }

    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        mainController.leaveGame(lis, getUserNickname(), game_id);
        return mainController.getGameController(game_id);
    }

    /**
     * Only for implementation purposes
     * @param mainController the game controller interface
     * @throws RemoteException
     */
    @Override
    public void performOnGameController(GameControllerInterface mainController) throws RemoteException {

    }
}