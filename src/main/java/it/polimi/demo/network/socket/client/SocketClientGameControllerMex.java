package it.polimi.demo.network.socket.client;

import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This abstract class represents a generic message that can be sent by the client to the server.
 */
public abstract class SocketClientGameControllerMex extends GenericControllerMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -5886817470118365739L;

    private final AuxMessage data = new AuxMessage();

    /**
     * Processes the message with the game controller.
     * @param gameCtrlInterface the game controller interface
     * @throws RemoteException in case of remote communication issues
     * @throws GameEndedException if the game has ended
     */
    public abstract void performOnGameController(GameControllerInterface gameCtrlInterface) throws RemoteException, GameEndedException;

    /**
     * Retrieves the user's nickname associated with the message.
     * @return the user's nickname, or null if data is null
     */
    public String getUserNickname() {
        return data.getUserNickname();
    }

    /**
     * Sets the user's nickname associated with the message.
     * @param userNickname the user's nickname
     */
    public void setUserNickname(String userNickname) {
        data.setUserNickname(userNickname);
    }
}



