package it.polimi.demo.observer;

import it.polimi.demo.model.chat.Message;

import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;


import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Listener extends Remote {

    void starterCardPlaced(GameModelImmutable model, Orientation orientation, String nick) throws RemoteException;

    void cardChosen(GameModelImmutable model, int which_card) throws RemoteException;

    void cardPlaced(GameModelImmutable model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException;

    void illegalMove(GameModelImmutable model) throws RemoteException;

    void illegalMoveBecauseOf(GameModelImmutable model, String reason_why) throws RemoteException;

    void cardDrawn(GameModelImmutable model, int index) throws RemoteException;

    void playerJoined(GameModelImmutable model) throws RemoteException;

    void playerLeft(GameModelImmutable model, String nick) throws RemoteException;

    void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException;

    void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;

    void gameIdNotExists(int gameid) throws RemoteException;

    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException;

    void gameStarted(GameModelImmutable gamemodel) throws RemoteException;

    void gameEnded(GameModelImmutable gamemodel) throws RemoteException;

    void secondLastRound(GameModelImmutable gamemodel) throws RemoteException;

    void lastRound(GameModelImmutable gamemodel) throws RemoteException;

    void messageSent(GameModelImmutable gameModel, String nickname, Message message) throws RemoteException;

    void nextTurn(GameModelImmutable gamemodel) throws RemoteException;

    void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException;

}
