package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.enumerations.Orientation;

import java.rmi.RemoteException;

/**
 * msgGrabbedTile class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a tile has been grabbed from the playground.
 */
public class msgCardPlaced extends SocketServerGenericMessage {

    private ModelView gamemodel;
    private int x;
    private int y;
    private Orientation orientation;

    /**
     * Constructor of the class.
     * @param gamemodel the immutable game model
     */
    public msgCardPlaced(ModelView gamemodel, int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.gamemodel = gamemodel;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game observer
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.cardPlaced(gamemodel, x, y, orientation);
    }
}
