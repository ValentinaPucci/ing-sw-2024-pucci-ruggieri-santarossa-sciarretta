package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;


public class SocketClientMsgPlaceCard extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -4335518806107022454L;
    int x;
    int y;
    private Orientation orientation;

    /**
     * Constructor of the class.
     * @param x
     * @param y
     * @param orientation
     */
    public SocketClientMsgPlaceCard(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.setMainControllerTarget(false);
    }

    /**
     * Only for implementation purposes.
     * @param observer the game observer
     * @param mainCtrlInterface the main controller interface
     * @return
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface performOnMainController(Listener observer, MainControllerInterface mainCtrlInterface) throws RemoteException {
        return null;
    }

    /**
     * Method to perform placeCard on the game controller.
     * @param gameController the game controller interface
     * @throws RemoteException
     * @throws GameEndedException
     */
    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.placeCard(gameController.getCurrentPlayer().getNickname(), x, y, orientation);
    }
}
