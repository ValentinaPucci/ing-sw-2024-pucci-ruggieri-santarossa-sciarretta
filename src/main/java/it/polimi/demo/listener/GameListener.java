package it.polimi.demo.listener;

import it.polimi.demo.model.chat.Message;

import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;


import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is used to notify the socket about the changes in the game
 */
public interface GameListener extends Remote {

    /**
     * This method is invoked when startUI wants to place the starter card.
     * @param model the game model
     * @param orientation the orientation of the starter card
     */
    void starterCardPlaced(GameModelImmutable model, Orientation orientation, String nick) throws RemoteException;

    /**
     * This method is invoked by gameUI when the player wants to choose a card
     * @param model the game model
     * @param which_card the index of the card the player wants to choose
     */
    void cardChosen(GameModelImmutable model, int which_card) throws RemoteException;

    /**
     * This method is invoked by gameUI when the player wants to place a card.
     * All the parameters are meant as indexes or coordinates.
     * @param model the game model
     * @param where_to_place_x the x coordinate of the cell where the player wants to place the card
     * @param where_to_place_y the y coordinate of the cell where the player wants to place the card
     * @param orientation
     */
    void cardPlaced(GameModelImmutable model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException;

    void illegalMove(GameModelImmutable model) throws RemoteException;

    /**
     * This method is invoked by gameUI when the player wants to draw a card.
     * @param model the game model
     * @param index the index of the card the player wants to draw
     */
    void cardDrawn(GameModelImmutable model, int index) throws RemoteException;

    /**
     * This method is used to notify the socket that a player has joined the game
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void playerJoined(GameModelImmutable model) throws RemoteException;

    /**
     * This method is used to notify the socket that a player has left the game
     * @param model is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that has left
     * @throws RemoteException if the reference could not be accessed
     */
    void playerLeft(GameModelImmutable model, String nick) throws RemoteException;

    /**
     * This method is used to notify the socket that a player has tried to join the game but the game is full
     * @param p is the player that has tried to join the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the socket that a player has reconnected to the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nickPlayerReconnected is the nickname of the player that has reconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException;

    /**
     * This method is used to notify the socket that a player has tried to join the game but the nickname is already in use
     * @param wantedToJoin is the player that has tried to join the game
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;

    /**
     * This method is used to notify the socket that the game id does not exist
     * @param gameid is the id of the game
     * @throws RemoteException if the reference could not be accessed
     */
    void gameIdNotExists(int gameid) throws RemoteException;


    /**
     * This is a generic error that can happen when a player is entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the reference could not be accessed
     */
    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    /**
     * This method is used to notify that the player is ready to start the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that is ready to start
     * @throws IOException if the reference could not be accessed
     */
    void playerIsReadyToStart(GameModelImmutable gamemodel, String nick) throws IOException;

    /**
     * This method is used to notify the socket that the game has started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void gameStarted(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the socket that the game has ended
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void gameEnded(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client entered in the last round
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void lastRound(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify that a message has been sent {@link Message}
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param msg is the message that has been sent
     * @throws RemoteException if the reference could not be accessed
     */
    void messageSent(GameModelImmutable gameModel, Message msg) throws RemoteException;

    /**
     * This method is used to notify that the next turn triggered
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void nextTurn(GameModelImmutable gamemodel) throws RemoteException;


    /**
     * This method is used to notify that a player has disconnected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that has disconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerDisconnected(GameModelImmutable gameModel, String nick) throws RemoteException;


    /**
     * This method is used to notify that only one player is connected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     * @throws RemoteException if the reference could not be accessed
     */
    void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException;


}
