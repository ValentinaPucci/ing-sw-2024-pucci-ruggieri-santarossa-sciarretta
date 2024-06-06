package it.polimi.demo.networking;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.flow.Dynamics;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public class ListenerHandlerClient implements Listener, Serializable {


    private Dynamics dynamics;

    public ListenerHandlerClient(Dynamics gui) {
        this.dynamics = gui;
    }

    @Override
    public void starterCardPlaced(GameModelImmutable model, Orientation orientation, String nick) throws RemoteException {
        dynamics.starterCardPlaced(model, orientation, nick);
    }

    @Override
    public void cardChosen(GameModelImmutable model, int which_card) throws RemoteException {
        dynamics.cardChosen(model, which_card);
    }

    @Override
    public void cardPlaced(GameModelImmutable model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        dynamics.cardPlaced(model, where_to_place_x, where_to_place_y, orientation);
    }

    @Override
    public void illegalMove(GameModelImmutable model) throws RemoteException {
        dynamics.illegalMove(model);
    }

    @Override
    public void illegalMoveBecauseOf(GameModelImmutable model, String reason_why) throws RemoteException {
        dynamics.illegalMoveBecauseOf(model, reason_why);
    }

    @Override
    public void cardDrawn(GameModelImmutable model, int index) throws RemoteException {
        dynamics.cardDrawn(model, index);
    }

    @Override
    public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {
        dynamics.playerJoined(gamemodel);
    }

    @Override
    public void playerLeft(GameModelImmutable gamemodel,String nick) throws RemoteException {
        dynamics.playerLeft(gamemodel,nick);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        dynamics.joinUnableGameFull(wantedToJoin, gamemodel);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        dynamics.joinUnableNicknameAlreadyIn(wantedToJoin);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        dynamics.gameIdNotExists(gameid);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        dynamics.genericErrorWhenEnteringGame(why);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {
        dynamics.playerIsReadyToStart(gamemodel, nick);
    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        dynamics.gameStarted(gamemodel);
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        dynamics.gameEnded(gamemodel);
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        dynamics.nextTurn(gamemodel);
    }

    @Override
    public void playerDisconnected(GameModelImmutable gameModel,String nick) throws RemoteException {
        dynamics.playerDisconnected(gameModel,nick);
    }

    @Override
    public void secondLastRound(GameModelImmutable gamemodel) throws RemoteException {
        dynamics.secondLastRound(gamemodel);
    }

    @Override
    public void lastRound(GameModelImmutable gamemodel) throws RemoteException {
        dynamics.lastRound(gamemodel);
    }

    @Override
    public void messageSent(GameModelImmutable gameModel, String nickname, Message message) throws RemoteException {
        dynamics.messageSent(gameModel, nickname, message);
    }

}