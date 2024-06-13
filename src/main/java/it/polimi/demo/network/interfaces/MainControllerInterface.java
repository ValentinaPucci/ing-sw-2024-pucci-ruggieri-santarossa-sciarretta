package it.polimi.demo.network.interfaces;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainControllerInterface extends Remote {

    GameControllerInterface createGame(Listener lis, String nick, int num_players) throws RemoteException;

    GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface joinRandomly(Listener lis, String nick) throws RemoteException;

    void setAsReady(Listener lis, String nick, int idGame) throws RemoteException;

    void placeStarterCard(Listener lis, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException;

    void chooseCard(Listener lis, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException;

    void placeCard(Listener lis, String nick, int x, int y, Orientation o, int idGame) throws RemoteException, GameEndedException;

    void drawCard(Listener lis, String nick, int index, int idGame) throws RemoteException, GameEndedException;

    void sendMessage(Listener lis, String nick, Message message, int idGame) throws RemoteException;

    void showOthersPersonalBoard(Listener lis, String nickname, int playerIndex, int gameId) throws RemoteException;

    void addPing(Listener lis, String nick, int idGame) throws RemoteException;

    void leaveGame(Listener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface getGameController(int idGame) throws RemoteException;
}