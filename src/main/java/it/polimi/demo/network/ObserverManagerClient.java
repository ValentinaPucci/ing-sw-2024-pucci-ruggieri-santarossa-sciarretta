package it.polimi.demo.network;

import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.network.utils.AuxFI;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;


public class ObserverManagerClient implements Listener, Serializable {

    private static final long serialVersionUID = -5070962929563880709L;
    private transient GameDynamic dynamic;

    public ObserverManagerClient(GameDynamic dyn) {
        this.dynamic = dyn;
    }

    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) {
        handleEvent(() -> dynamic.starterCardPlaced(model, orientation, nick));
    }

    @Override
    public void cardChosen(ModelView model, int which_card) {
        handleEvent(() -> dynamic.cardChosen(model, which_card));
    }

    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) {
        handleEvent(() -> dynamic.cardPlaced(model, where_to_place_x, where_to_place_y, orientation));
    }

    @Override
    public void illegalMove(ModelView model) {
        handleEvent(() -> dynamic.illegalMove(model));
    }

    @Override
    public void successfulMove(ModelView model, Coordinate coord) {
        handleEvent(() -> dynamic.successfulMove(model, coord));
    }

    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) {
        handleEvent(() -> dynamic.illegalMoveBecauseOf(model, reason_why));
    }

    @Override
    public void cardDrawn(ModelView model, int index) {
        handleEvent(() -> dynamic.cardDrawn(model, index));
    }

    @Override
    public void playerJoined(ModelView model) {
        handleEvent(() -> dynamic.playerJoined(model));
    }

    @Override
    public void playerLeft(ModelView model, String nick) {
        handleEvent(() -> dynamic.playerLeft(model, nick));
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView model) {
        handleEvent(() -> dynamic.joinUnableGameFull(wantedToJoin, model));
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) {
        handleEvent(() -> dynamic.joinUnableNicknameAlreadyIn(wantedToJoin));
    }

    @Override
    public void gameIdNotExists(int game_id) {
        handleEvent(() -> dynamic.gameIdNotExists(game_id));
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) {
        handleEvent(() -> dynamic.genericErrorWhenEnteringGame(why));
    }

    @Override
    public void playerIsReadyToStart(ModelView model, String nick) throws IOException, RemoteException {
        handleEvent(() -> dynamic.playerIsReadyToStart(model, nick));
    }

    @Override
    public void gameStarted(ModelView model) {
        handleEvent(() -> dynamic.gameStarted(model));
    }

    @Override
    public void gameEnded(ModelView model) {
        handleEvent(() -> dynamic.gameEnded(model));
    }

    @Override
    public void nextTurn(ModelView model) {
        handleEvent(() -> dynamic.nextTurn(model));
    }

    @Override
    public void playerDisconnected(ModelView model, String nick) {
        handleEvent(() -> dynamic.playerDisconnected(model, nick));
    }

    @Override
    public void showOthersPersonalBoard(ModelView model, String playerNickname, int playerIndex) {
        handleEvent(() -> {
            try {
                dynamic.showOthersPersonalBoard(model, playerNickname, playerIndex);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void secondLastRound(ModelView model) {
        handleEvent(() -> dynamic.secondLastRound(model));
    }

    @Override
    public void lastRound(ModelView model) {
        handleEvent(() -> dynamic.lastRound(model));
    }

    @Override
    public void messageSent(ModelView model, String nickname, Message message) {
        handleEvent(() -> dynamic.messageSent(model, nickname, message));
    }

    private void handleEvent(Runnable eventAction) {
        AuxFI.handleEvent(eventAction);
    }
}
