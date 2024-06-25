package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class to send the placeStarterCard message to the server.
 */
public class GCMsgPlaceStarterCard extends GCMsg implements Serializable {
    @Serial
    private static final long serialVersionUID = -1739537774448253366L;
    private Orientation orientation;

    /**
     * Constructor of the class.
     * @param orientation
     */
    public GCMsgPlaceStarterCard(Orientation orientation) {
        this.orientation = orientation;
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
