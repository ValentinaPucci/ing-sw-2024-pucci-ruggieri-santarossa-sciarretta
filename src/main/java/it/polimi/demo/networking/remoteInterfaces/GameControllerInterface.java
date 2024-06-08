package it.polimi.demo.networking.remoteInterfaces;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface GameControllerInterface extends Remote {

    void placeStarterCard(String nickname, Orientation orientation) throws GameEndedException, RemoteException;

    void chooseCardFromHand(String nick, int index) throws RemoteException;

    void placeCard(String nickname, int x, int y, Orientation orientation) throws RemoteException, GameEndedException;

    void drawCard(String player_nickname, int index) throws RemoteException, GameEndedException;

    boolean isMyTurn(String nick) throws RemoteException;

    Player getIdentityOfPlayer(String nickname) throws RemoteException;

    int getGameId() throws RemoteException;

    LinkedList<Player> getConnectedPlayers() throws RemoteException;

    Player getCurrentPlayer() throws RemoteException;

    void sendMessage(String nick, Message mess) throws RemoteException;

    int getNumConnectedPlayers() throws RemoteException;

    void leave(Listener lis, String nick) throws RemoteException;

    void setError(String s) throws RemoteException;

    GameStatus getStatus() throws RemoteException;

    void playerIsReadyToStart(String nickname) throws RemoteException;

    void addPing(String nickname, Listener listener) throws RemoteException;

    int getNumPlayersToPlay() throws RemoteException;
}