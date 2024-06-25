package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class MCMsg extends C2SGenericMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 7667308065938007850L;

    /**
     * Processes the message with the main controller and observer.
     *
     * @param observer          the game observer
     * @param mainCtrlInterface the main controller interface
     * @return the game controller interface after processing the message
     * @throws RemoteException in case of remote communication issues
     */
    public abstract GameControllerInterface performOnMainController(Listener observer, MainControllerInterface mainCtrlInterface) throws RemoteException;
}
