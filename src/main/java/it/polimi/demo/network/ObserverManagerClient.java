package it.polimi.demo.network;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.dynamic.GameDynamic;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class ObserverManagerClient implements Listener, Serializable {

    @Serial
    private static final long serialVersionUID = -5070962929563880709L;
    private GameDynamic dynamic;

    public ObserverManagerClient(GameDynamic gui) {
        this.dynamic = gui;
    }

    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) throws RemoteException {
        dynamic.starterCardPlaced(model, orientation, nick);
    }

    @Override
    public void cardChosen(ModelView model, int which_card) throws RemoteException {
        dynamic.cardChosen(model, which_card);
    }

    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        dynamic.cardPlaced(model, where_to_place_x, where_to_place_y, orientation);
    }

    @Override
    public void illegalMove(ModelView model) throws RemoteException {
        dynamic.illegalMove(model);
    }

    @Override
    public void successfulMove(ModelView model, Coordinate coord) throws RemoteException {
        dynamic.successfulMove(model, coord);
    }

    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) throws RemoteException {
        dynamic.illegalMoveBecauseOf(model, reason_why);
    }

    @Override
    public void cardDrawn(ModelView model, int index) throws RemoteException {
        dynamic.cardDrawn(model, index);
    }

    @Override
    public void playerJoined(ModelView model) throws RemoteException {
        dynamic.playerJoined(model);
    }

    @Override
    public void playerLeft(ModelView model, String nick) throws RemoteException {
        dynamic.playerLeft(model, nick);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView model) throws RemoteException {
        dynamic.joinUnableGameFull(wantedToJoin, model);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        dynamic.joinUnableNicknameAlreadyIn(wantedToJoin);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        dynamic.gameIdNotExists(gameid);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        dynamic.genericErrorWhenEnteringGame(why);
    }

    @Override
    public void playerIsReadyToStart(ModelView model, String nick) throws IOException {
        dynamic.playerIsReadyToStart(model, nick);
    }

    @Override
    public void gameStarted(ModelView model) throws RemoteException {
        dynamic.gameStarted(model);
    }

    @Override
    public void gameEnded(ModelView model) throws RemoteException {
        dynamic.gameEnded(model);
    }

    @Override
    public void nextTurn(ModelView model) throws RemoteException {
        dynamic.nextTurn(model);
    }

    @Override
    public void playerDisconnected(ModelView model, String nick) throws RemoteException {
        dynamic.playerDisconnected(model, nick);
    }

    @Override
    public void showOthersPersonalBoard(ModelView model, String playerNickname, int playerIndex) throws RemoteException {
        dynamic.showOthersPersonalBoard(model, playerNickname, playerIndex);
    }

    @Override
    public void secondLastRound(ModelView model) throws RemoteException {
        dynamic.secondLastRound(model);
    }

    @Override
    public void lastRound(ModelView model) throws RemoteException {
        dynamic.lastRound(model);
    }

    @Override
    public void messageSent(ModelView model, String nickname, Message message) throws RemoteException {
        dynamic.messageSent(model, nickname, message);
    }
}


