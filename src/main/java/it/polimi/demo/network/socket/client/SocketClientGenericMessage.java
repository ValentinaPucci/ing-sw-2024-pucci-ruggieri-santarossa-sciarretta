package it.polimi.demo.network.socket.client;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class SocketClientGenericMessage implements Serializable {

    public String nick;
    protected boolean isMessageForMainController;
    protected boolean isHeartbeat = false;

    /**
     * Executes the corresponding action for the message.
     * @param lis the game observer
     * @param mainController the main controller interface
     * @return the game controller interface
     * @throws RemoteException if there is a remote exception
     */
    public abstract GameControllerInterface perform(Listener lis, MainControllerInterface mainController) throws RemoteException;

    /**
     * Executes the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is a remote exception
     * @throws GameEndedException if the game has ended
     */
    public abstract void perform(GameControllerInterface gameController) throws RemoteException, GameEndedException;

    /**
     * Checks if the message is intended for the main controller.
     * @return true if the message is intended for the main controller, false otherwise
     */
    public boolean isMessageForMainController() {
        return isMessageForMainController;
    }

    /**
     * Returns the nickname associated with the message.
     * @return the nickname
     */
    public String getNick() {
        return nick;
    }

    /**
     * @return if it's a ping message
     */
    public boolean isHeartbeat() {
        return isHeartbeat;
    }

}
