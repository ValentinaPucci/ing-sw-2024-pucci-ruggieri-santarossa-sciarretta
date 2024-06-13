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
    public void playerJoined(ModelView gamemodel) throws RemoteException {
        dynamic.playerJoined(gamemodel);
    }

    @Override
    public void playerLeft(ModelView gamemodel, String nick) throws RemoteException {
        dynamic.playerLeft(gamemodel, nick);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView gamemodel) throws RemoteException {
        dynamic.joinUnableGameFull(wantedToJoin, gamemodel);
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
    public void playerIsReadyToStart(ModelView gamemodel, String nick) throws IOException {
        dynamic.playerIsReadyToStart(gamemodel, nick);
    }

    @Override
    public void gameStarted(ModelView gamemodel) throws RemoteException {
        dynamic.gameStarted(gamemodel);
    }

    @Override
    public void gameEnded(ModelView gamemodel) throws RemoteException {
        dynamic.gameEnded(gamemodel);
    }

    @Override
    public void nextTurn(ModelView gamemodel) throws RemoteException {
        dynamic.nextTurn(gamemodel);
    }

    @Override
    public void playerDisconnected(ModelView gameModel, String nick) throws RemoteException {
        dynamic.playerDisconnected(gameModel, nick);
    }

    @Override
    public void showOthersPersonalBoard(ModelView modelView, String playerNickname, int playerIndex) throws RemoteException {
        dynamic.showOthersPersonalBoard(modelView, playerNickname, playerIndex);
    }

    @Override
    public void secondLastRound(ModelView gamemodel) throws RemoteException {
        dynamic.secondLastRound(gamemodel);
    }

    @Override
    public void lastRound(ModelView gamemodel) throws RemoteException {
        dynamic.lastRound(gamemodel);
    }

    @Override
    public void messageSent(ModelView gameModel, String nickname, Message message) throws RemoteException {
        dynamic.messageSent(gameModel, nickname, message);
    }
}