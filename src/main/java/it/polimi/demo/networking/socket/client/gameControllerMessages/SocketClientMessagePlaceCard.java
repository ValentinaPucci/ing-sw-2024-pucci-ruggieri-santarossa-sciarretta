package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

/**
 * SocketClientMessagePlaceCard class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating the positioning of a tile on the player's shelf.
 */
public class SocketClientMessagePlaceCard extends SocketClientGenericMessage {
    int x;
    int y;
    private Orientation orientation;

    /**
     *
     * @param x
     * @param y
     * @param orientation
     */
    public SocketClientMessagePlaceCard(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.isMessageForMainController = false;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @param mainController the main controller of the application
     * @return the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.placeCard(gameController.getCurrentPlayer().getNickname(), x, y, orientation);
    }
}
