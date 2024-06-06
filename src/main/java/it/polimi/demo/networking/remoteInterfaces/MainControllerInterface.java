package it.polimi.demo.networking.remoteInterfaces;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainControllerInterface extends Remote {

    GameControllerInterface createGame(Listener lis, String nick, int num_players) throws RemoteException;

    GameControllerInterface joinGame(Listener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface joinFirstAvailableGame(Listener lis, String nick) throws RemoteException;

    GameControllerInterface setAsReady(Listener modelInvokedEvents, String nick, int idGame) throws RemoteException;

    GameControllerInterface placeStarterCard(Listener modelInvokedEvents, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface chooseCard(Listener modelInvokedEvents, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface placeCard(Listener modelInvokedEvents, String nick, int x, int y, Orientation o, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface drawCard(Listener modelInvokedEvents, String nick, int index, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface sendMessage(Listener modelInvokedEvents, String nick, Message message, int idGame) throws RemoteException;

    void addPing(Listener modelInvokedEvents, String nick, int idGame) throws RemoteException;

    GameControllerInterface leaveGame(Listener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface getGameController(int idGame) throws RemoteException;


}