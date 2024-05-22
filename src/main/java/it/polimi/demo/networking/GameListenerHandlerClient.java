package it.polimi.demo.networking;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.flow.Flow;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameListenerHandlerClient implements GameListener, Serializable {


    private Flow flow;

    public GameListenerHandlerClient(Flow gui) {
        this.flow = gui;
    }


    @Override
    public void placeStarterCard(GameModelImmutable model, Orientation orientation) {
        flow.placeStarterCard(model, orientation);
    }

    @Override
    public void chooseCard(GameModelImmutable model, int which_card) {
        flow.chooseCard(model, which_card);
    }

    @Override
    public void placeCard(GameModelImmutable model, int where_to_place_x, int where_to_place_y, Orientation orientation) {
        flow.placeCard(model, where_to_place_x, where_to_place_y, orientation);
    }

    @Override
    public void drawCard(GameModelImmutable model, int index) {
        flow.drawCard(model, index);
    }

    @Override
    public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {
        flow.playerJoined(gamemodel);
    }

    @Override
    public void playerLeft(GameModelImmutable gamemodel,String nick) throws RemoteException {
        flow.playerLeft(gamemodel,nick);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gamemodel) throws RemoteException {
        flow.joinUnableGameFull(wantedToJoin, gamemodel);
    }

    @Override
    public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {
        flow.playerReconnected(gamemodel, nickPlayerReconnected);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        flow.joinUnableNicknameAlreadyIn(wantedToJoin);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        flow.gameIdNotExists(gameid);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        flow.genericErrorWhenEnteringGame(why);
    }

    @Override
    public void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException {
        flow.playerIsReadyToStart(gamemodel, nick);
    }

    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        flow.gameStarted(gamemodel);
        //setModel(gamemodel);
    }

    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        flow.gameEnded(gamemodel);
    }

    @Override
    public void sentMessage(GameModelImmutable gameModel, Message msg) throws RemoteException {
        flow.sentMessage(gameModel, msg);
    }

    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        flow.nextTurn(gamemodel);
    }

    @Override
    public void playerDisconnected(GameModelImmutable gameModel,String nick) throws RemoteException {
        flow.playerDisconnected(gameModel,nick);
    }


    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        flow.onlyOnePlayerConnected(gameModel,secondsToWaitUntilGameEnded);
    }

    @Override
    public void lastCircle(GameModelImmutable gamemodel) throws RemoteException {
        flow.lastCircle(gamemodel);
    }

}