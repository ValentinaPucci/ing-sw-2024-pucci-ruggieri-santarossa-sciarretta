package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.Player;

import java.rmi.RemoteException;

/**
 * msgJoinUnableNicknameAlreadyIn class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player was unable to join the game because the nickname is already in use.
 */
public class msgJoinUnableNicknameAlreadyIn extends SocketServerGenericMessage {


    private Player wantedToJoin;

    /**
     * Constructor of the class.
     * @param wantedToJoin the player who wanted to join the game
     */
    public msgJoinUnableNicknameAlreadyIn(Player wantedToJoin) {
        this.wantedToJoin = wantedToJoin;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game observer
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.joinUnableNicknameAlreadyIn(wantedToJoin);
    }
}
