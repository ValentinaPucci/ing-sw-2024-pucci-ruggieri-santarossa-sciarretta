package it.polimi.demo.network.socket.client;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This abstract class represents a generic message that can be sent by the client to the server.
 */
public abstract class SocketClientGenericMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -5886817470118365739L;

    private MessageData data;

    /**
     * Processes the message with the main controller and observer.
     * @param observer the game observer
     * @param mainCtrlInterface the main controller interface
     * @return the game controller interface after processing the message
     * @throws RemoteException in case of remote communication issues
     */
    public abstract GameControllerInterface performOnMainController(Listener observer, MainControllerInterface mainCtrlInterface) throws RemoteException;

    /**
     * Processes the message with the game controller.
     * @param gameCtrlInterface the game controller interface
     * @throws RemoteException in case of remote communication issues
     * @throws GameEndedException if the game has ended
     */
    public abstract void performOnGameController(GameControllerInterface gameCtrlInterface) throws RemoteException, GameEndedException;

    /**
     * Checks if the message is aimed at the main controller.
     * @return true if the message is for the main controller, false otherwise
     */
    public boolean isMainControllerTarget() {
        return data != null && Boolean.TRUE.equals(data.isMainControllerTarget());
    }

    /**
     * Retrieves the user's nickname associated with the message.
     * @return the user's nickname, or null if data is null
     */
    public String getUserNickname() {
        return data != null ? data.nickname() : null;
    }

    /**
     * Sets the user's nickname associated with the message.
     * @param userNickname the user's nickname
     */
    public void setUserNickname(String userNickname) {
        if (data != null) {
            this.data = new MessageData(userNickname, data.isMainControllerTarget());
        } else {
            this.data = new MessageData(userNickname, false);
        }
    }

    /**
     * Sets whether the message is aimed at the main controller.
     * @param mainControllerTarget true if the message is for the main controller, false otherwise
     */
    public void setMainControllerTarget(boolean mainControllerTarget) {
        if (data != null) {
            this.data = new MessageData(data.nickname(), mainControllerTarget);
        } else {
            this.data = new MessageData(null, mainControllerTarget);
        }
    }
}



