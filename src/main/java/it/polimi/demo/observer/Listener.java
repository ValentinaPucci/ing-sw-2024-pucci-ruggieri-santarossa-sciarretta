package it.polimi.demo.observer;

import it.polimi.demo.model.chat.Message;

import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;


import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Listener extends Remote {

    void starterCardPlaced(ModelView model, Orientation orientation, String nick) throws RemoteException;

    void cardChosen(ModelView model, int which_card) throws RemoteException;

    void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException;

    void illegalMove(ModelView model) throws RemoteException;

    void successfulMove(ModelView model, Coordinate coord) throws RemoteException;

    void illegalMoveBecauseOf(ModelView model, String reason_why) throws RemoteException;

    void cardDrawn(ModelView model, int index) throws RemoteException;

    void playerJoined(ModelView model) throws RemoteException;

    void playerLeft(ModelView model, String nick) throws RemoteException;

    void joinUnableGameFull(Player p, ModelView gamemodel) throws RemoteException;

    void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;

    void gameIdNotExists(int gameid) throws RemoteException;

    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    void playerIsReadyToStart(ModelView gamemodel, String nick) throws IOException;

    void gameStarted(ModelView gamemodel) throws RemoteException;

    void gameEnded(ModelView gamemodel) throws RemoteException;

    void secondLastRound(ModelView gamemodel) throws RemoteException;

    void lastRound(ModelView gamemodel) throws RemoteException;

    void messageSent(ModelView gameModel, String nickname, Message message) throws RemoteException;

    void nextTurn(ModelView gamemodel) throws RemoteException;

    void playerDisconnected(ModelView gameModel, String nick) throws RemoteException;

    void showOthersPersonalBoard(ModelView modelView, String playerNickname, int playerIndex) throws RemoteException;
}
