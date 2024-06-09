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


public class SocketClientMsgPlaceStarterCard extends SocketClientGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -1739537774448253366L;
    private Orientation orientation;

    /**
     * Constructor of the class.
     * @param orientation
     */
    public SocketClientMsgPlaceStarterCard(Orientation orientation) {
        this.orientation = orientation;
        this.setMainControllerTarget(false);
    }

    /**
     * Only for implementation purposes
     * @param lis the game observer
     * @param mainController the main controller interface
     * @return
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface performOnMainController(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to perform placeStarterCard on the game controller.
     * @param gameController the game controller interface
     * @throws RemoteException
     * @throws GameEndedException
     */
    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.placeStarterCard(gameController.getCurrentPlayer().getNickname(), orientation);
    }
}
