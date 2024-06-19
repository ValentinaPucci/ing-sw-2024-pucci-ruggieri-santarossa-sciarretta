package it.polimi.demo.network.socket.client;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class SocketClientGenericMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -5886817470118365739L;
    private String nickname;
    // The main controller target is the main controller if true, the message is for the MainController
    // and not for the gameController
    private boolean isMainControllerTarget;
    private boolean heartbeatMessage = false;

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
        return isMainControllerTarget;
    }

    /**
     * Retrieves the user's nickname associated with the message.
     * @return the user's nickname
     */
    public String getUserNickname() {
        return nickname;
    }

    /**
     * Determines if the message is a heartbeat signal.
     * @return true if it is a heartbeat message, false otherwise
     */
    public boolean isHeartbeatMessage() {
        return heartbeatMessage;
    }

    // Setters for the private fields

    /**
     * Setters for the private fields
     * @param userNickname
     */
    public void setUserNickname(String userNickname) {
        this.nickname= userNickname;
    }

    /**
     * Setters for the private fields
     * @param mainControllerTarget
     */
    public void setMainControllerTarget(boolean mainControllerTarget) {
        this.isMainControllerTarget = mainControllerTarget;
    }

    /**
     * Setters for the private fields
     * @param heartbeatMessage
     */
    public void setHeartbeatMessage(boolean heartbeatMessage) {
        this.heartbeatMessage = heartbeatMessage;
    }

}
