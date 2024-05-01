package it.polimi.demo.listener;

import it.polimi.demo.listener.Listener;

import java.rmi.RemoteException;

public interface UIListener extends Listener {
    void refreshStartUI();

    /**
     * This method is invoked when startUI wants to create a new game.
     */
    void createGame(String nickname, int numberOfPlayers)throws RemoteException;

    /**
     * This method is invoked when startUI wants to join a game.
     */
    void joinGame(int gameID, String username);

    /**
     * This method is called when the GameUI wants to perform a turn.
     */
    void performTurn(int column);

    void exit();
}
