package it.polimi.demo.network.socket.server;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.network.socket.client.serverToClientMessages.*;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
/**
 * This class is used to handle the messages that are sent to the client
 */

public class GameListenersHandlerSocket implements Listener, Serializable {
    @Serial
    private static final long serialVersionUID = -44724272240516582L;
    private final transient ObjectOutputStream ob_out;


    public GameListenersHandlerSocket(ObjectOutputStream output) {
        ob_out = output;
    }

    /**
     * This method is used to write on the ObjectOutputStream the messages
     */
    private void writeObject(Object message) throws IOException {
        ob_out.writeObject(message);
        ob_out.flush();
        ob_out.reset();
    }

    @Override
    public void starterCardPlaced(ModelView gamemodel, Orientation orientation, String nick) throws RemoteException {
        try {
            writeObject(new msgStarterCardPlaced(gamemodel, orientation));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cardChosen(ModelView model, int which_card) throws RemoteException {
        try {
            writeObject(new msgCardChosen(model, which_card));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public void cardDrawn(ModelView model, int index) throws RemoteException {
        try {
            writeObject(new msgCardDrawn(model, index));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void playerJoined(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgPlayerJoined(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerLeft(ModelView gamemodel, String nick) throws RemoteException {
        try {
            writeObject(new msgPlayerLeft(gamemodel, nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinUnableGameFull(Player p, ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgJoinUnableGameFull(p, gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        try {
            writeObject(new msgJoinUnableNicknameAlreadyIn(wantedToJoin));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        try {
            writeObject(new msgGameIdNotExists(gameid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        try {
            writeObject(new msgGenericErrorWhenEntryingGame(why));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerIsReadyToStart(ModelView model, String nick) throws RemoteException {
        try {
            writeObject(new msgPlayerIsReadyToStart(model, nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameStarted(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgGameStarted(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gameEnded(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgGameEnded(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void secondLastRound(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgSecondLastRound(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);}
    }

    @Override
    public void cardPlaced(ModelView gamemodel, int x, int y, Orientation orientation) throws RemoteException {
        try {
            writeObject(new msgCardPlaced(gamemodel, x, y, orientation));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void illegalMove(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgIllegalMove(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulMove(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgSuccessfulMove(gamemodel));
        } catch (IOException e) {

        }
    }

    @Override
    public void illegalMoveBecauseOf(ModelView gamemodel, String reason_why) throws RemoteException {
        try {
            writeObject(new msgIllegalMoveBecauseOf(gamemodel, reason_why));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nextTurn(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgNextTurn(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerDisconnected(ModelView gameModel, String nick) throws RemoteException {
        try {
            writeObject(new msgPlayerDisconnected(gameModel, nick));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lastRound(ModelView gamemodel) throws RemoteException {
        try {
            writeObject(new msgLastRound(gamemodel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void messageSent(ModelView gameModel, String nickname, Message message) throws RemoteException {
        try {
            writeObject(new msgMessageSent(gameModel, nickname, message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showOthersPersonalBoard(ModelView modelView, String playerNickname, int playerIndex) throws RemoteException {
        try {
            writeObject(new msgShowOthersPersonalBoards(modelView, playerNickname, playerIndex));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    }
