package it.polimi.demo.networking.remoteInterfaces;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainControllerInterface extends Remote {

    GameControllerInterface createGame(GameListener lis, String nick, int num_players) throws RemoteException;

    GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException;

    GameControllerInterface setAsReady(GameListener modelInvokedEvents, String nick, int idGame) throws RemoteException;

    GameControllerInterface placeStarterCard(GameListener modelInvokedEvents, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface chooseCard(GameListener modelInvokedEvents, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface placeCard(GameListener modelInvokedEvents, String nick, int x, int y, Orientation o, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface drawCard(GameListener modelInvokedEvents, String nick, int index, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface sendMessage(GameListener modelInvokedEvents, String nick, Message message, int idGame) throws RemoteException;

    void addPing(GameListener modelInvokedEvents, String nick, int idGame) throws RemoteException;

    GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface getGameController(int idGame) throws RemoteException;


}