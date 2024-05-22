package it.polimi.demo.networking.rmi.remoteInterfaces;

import it.polimi.demo.controller.GameController;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.view.GameDetails;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

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
    GameControllerInterface createGame(GameListener lis, String nick, int num_players, int gameID) throws RemoteException;

//    /**
//     * This method joins the first available game
//     * @param lis the GameListener of the player {@link GameListener}
//     * @param nick the nickname of the player
//     * @return the GameControllerInterface of the game {@link GameControllerInterface}
//     * @throws RemoteException if the connection fails
//     */
//    GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException;

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


    List<GameDetails> getGamesDetails();

    Map<Integer, GameController> getGames();

}