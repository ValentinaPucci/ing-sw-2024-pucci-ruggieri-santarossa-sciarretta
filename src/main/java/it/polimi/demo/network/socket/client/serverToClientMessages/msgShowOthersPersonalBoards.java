package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgShowOthersPersonalBoards extends SocketServerGenericMessage{

    @Serial
    private static final long serialVersionUID = 7914302356958980212L;
    private ModelView gameModel;
    private int playerIndex;
    private String playerNickname;

    public msgShowOthersPersonalBoards(ModelView gameModel, String playerNickname, int playerIndex) {
        super("Card chosen");
        this.gameModel = gameModel;
        this.playerIndex = playerIndex;
        this.playerNickname = playerNickname;
    }
    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.showOthersPersonalBoard(gameModel, playerNickname, playerIndex);
    }
}
