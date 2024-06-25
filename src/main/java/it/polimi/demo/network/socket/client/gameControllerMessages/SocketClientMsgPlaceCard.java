package it.polimi.demo.network.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGameControllerMex;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class to send the place card message to the server.

 */
public class SocketClientMsgPlaceCard extends SocketClientGameControllerMex implements Serializable {
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
