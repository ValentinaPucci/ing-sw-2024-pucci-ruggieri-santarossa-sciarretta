package it.polimi.demo.listener;

import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameListener extends Remote {

    /**
     * This method is used to notify the client that a player has joined the game
     * @param gamemodel is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void playerJoined(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a player has left the game
     * @param game_model is the game model
     * @param nickname is the nickname of the player that has left
     * @throws RemoteException if the reference could not be accessed
     */
    void playerLeft(GameModelImmutable game_model, String nickname) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game but the game is full
     * @param player is the player that has tried to join the game
     * @param game_model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableGameFull(Player player, GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify the client that a player has reconnected to the game
     * @param game_model is the game model
     * @param nick_player_reconnected is the nickname of the player that has reconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerReconnected(GameModelImmutable game_model, String nick_player_reconnected) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game
     * but the nickname is already in use
     * @param wantedToJoin is the player that has tried to join the game
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException;

    /**
     * This method is used to notify the client that the game id does not exist
     * @param game_id is the id of the game
     * @throws RemoteException if the reference could not be accessed
     */
    void gameIdNotExists(int game_id) throws RemoteException;

    /**
     * This is a generic error that can happen when a player is entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the reference could not be accessed
     */
    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    /**
     * This method is used to notify that the player is ready to start the game
     * @param game_model is the game model
     * @param nickname is the nickname of the player that is ready to start
     * @throws IOException if the reference could not be accessed
     */
    void playerIsReadyToStart(GameModelImmutable game_model, String nickname) throws IOException;

    /**
     * This method is used to notify the client that the common objective cards have been
     * extracted in the game. It is a part of the GameListener interface and is expected
     * to be implemented by the client-side game listener.
     *
     * @param gamemodel The immutable version of the game model, which provides a snapshot of the game state at the time of the method call.
     * @throws RemoteException If a remote communication error occurs. This is a checked exception that must be declared in the method signature or caught within the method.
     */
    void commonObjectiveCardsExtracted(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a resource cards have been
     * extracted from the deck and add that card to his hand.
     *
     * @param gamemodel The immutable version of the game model,
     *                  which provides a snapshot of the game state at the time of the method call.
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromDeck(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a resource card have been
     * extracted from the table and add that card to his hand.
     *
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromTable(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a gold card have been
     * extracted from the deck and add that card to his hand.
     *
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromDeck(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a gold card have been
     * extracted from the table and add that card to his hand.
     *
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromTable(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract an objective card
     * from an empty deck.
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void objectiveCardExtractedFromEmptyDeck(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a resource card
     * from an empty deck.
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromEmptyDeck(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a resource card
     * from an empty table.
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromEmptyTable(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a gold card
     * from an empty deck.
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromEmptyDeck(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a gold card
     * from an empty table.
     * @param gamemodel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromEmptyTable(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that a card has been placed on a player's personal board.
     * It is a part of the GameListener interface and is expected to be implemented by the client-side game listener.
     *
     * @param gamemodel The immutable version of the game model, which provides a snapshot of the game state at the time of the method call.
     * @throws RemoteException If a remote communication error occurs. This is a checked exception that must be declared in the method signature or caught within the method.
     */
    void cardPlacedOnPersonalBoard(GameModelImmutable gamemodel) throws RemoteException;

    /**
     * This method is used to notify the client that the game has started
     * @param game_model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void gameStarted(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify the client that the game has ended
     * @param game_model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void gameEnded(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify that a message has been sent
     * @param game_model is the game model
     * @param msg is the message that has been sent
     * @throws RemoteException if the reference could not be accessed
     */
    void sentMessage(GameModelImmutable game_model, Message msg) throws RemoteException;


    /**
     * This method is used to notify that a player has disconnected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param nickname is the nickname of the player that has disconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerDisconnected(GameModelImmutable gameModel, String nickname) throws RemoteException;

    /**
     * This method is used to notify that the next turn triggered
     * @param game_model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void nextTurn(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify the client that a player has moved on the common board.
     * It is a part of the GameListener interface and is expected to be implemented by the client-side game listener.
     *
     * @param game_model The immutable version of the game model, which provides a snapshot of the game state at the time of the method call.
     * @throws RemoteException If a remote communication error occurs. This is a checked exception that must be declared in the method signature or caught within the method.
     */
    void playerHasMovedOnCommonBoard(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify that only one player is connected
     * @param gameModel is the game model
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     * @throws RemoteException if the reference could not be accessed
     */
    void onlyOnePlayerConnected(GameModelImmutable gameModel,
                                int secondsToWaitUntilGameEnded) throws RemoteException;

    /**
     * This method is used to notify that the last round has started
     * @param game_model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void lastRound(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify that the second last round has started
     * It is the moment when a player reaches 20 points
     * @param game_model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void secondLastRound(GameModelImmutable game_model) throws RemoteException;

}
