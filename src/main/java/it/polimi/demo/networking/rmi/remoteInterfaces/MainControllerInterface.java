package it.polimi.demo.networking.rmi.remoteInterfaces;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IT IS THE SKELETON --> VIRTUAL VIEW
 * This interface contains the events regarding the list of games
 */
public interface MainControllerInterface extends Remote {

    /**
     * This method creates a new game and add it to the GameListener list
     *
     * @param lis  the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @return the GameControllerInterface of the game {@link GameControllerInterface}
     * @throws RemoteException if the connection fails
     */
    GameControllerInterface createGame(GameListener lis, String nick, int num_players) throws RemoteException;

    /**
     * This method joins a specific game
     *
     * @param lis    the GameListener of the player {@link GameListener}
     * @param nick   the nickname of the player
     * @param idGame the id of the game
     * @return the GameControllerInterface of the game {@link GameControllerInterface}
     * @throws RemoteException if the connection fails
     */
    GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException;

    GameControllerInterface setAsReady(GameListener modelInvokedEvents, String nick, int idGame) throws RemoteException;

    GameControllerInterface placeStarterCard(GameListener modelInvokedEvents, String nick, Orientation o, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface chooseCard(GameListener modelInvokedEvents, String nick, int cardIndex, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface placeCard(GameListener modelInvokedEvents, String nick, int x, int y, Orientation o, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface drawCard(GameListener modelInvokedEvents, String nick, int index, int idGame) throws RemoteException, GameEndedException;

    GameControllerInterface sendMessage(GameListener modelInvokedEvents, String nick, Message message, int idGame) throws RemoteException;

    void addPing(GameListener modelInvokedEvents, String nick, int idGame) throws RemoteException;

    /**
     * This method reconnects a player to a specific game
     *
     * @param lis    the GameListener of the player {@link GameListener}
     * @param nick   the nickname of the player
     * @param idGame the id of the game
     * @return the GameControllerInterface of the game {@link GameControllerInterface}
     * @throws RemoteException if the connection fails
     */
    GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException;


    /**
     * This method leaves a specific game
     *
     * @param lis    the GameListener of the player {@link GameListener}
     * @param nick   the nickname of the player
     * @param idGame the id of the game
     * @return the GameControllerInterface of the game {@link GameControllerInterface}
     * @throws RemoteException if the connection fails
     */
    GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException;

    GameControllerInterface getGameController(int idGame) throws RemoteException;


}