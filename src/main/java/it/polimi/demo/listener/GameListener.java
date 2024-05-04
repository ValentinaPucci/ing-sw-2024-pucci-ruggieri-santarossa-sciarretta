package it.polimi.demo.listener;

import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameListener extends Remote {

    /**
     * This method is used to inform the client when a new player has entered the game.
     * @param gameModel is the game model interface
     * @throws RemoteException if the reference is inaccessible
     */
    void newPlayerHasJoined(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is employed to inform the client that a player has exited the game
     * @param game_model is the game model interface
     * @param nickname player's nickname who has exited
     * @throws RemoteException;
     */
    void playerAbandoningGame(GameModelImmutable game_model, String nickname) throws RemoteException;

    /**
     * This method is used to inform the client that a player attempted to join the game, but the game is at maximum capacity
     * @param player is the player that has attempted to join the game
     * @param game_model is the game model interface
     * @throws RemoteException;
     */
    void failedJoinFullGame(Player player, GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify the client that a player has reconnected to the game
     * @param game_model is the game model interface
     * @param nickname_reconnected is the player's nickname that has reconnected
     * @throws RemoteException;
     */
    void playerReconnected(GameModelImmutable game_model, String nickname_reconnected) throws RemoteException;

    /**
     * This method is used to notify the client that a player has attempted to join the game,
     * but the chosen nickname is already in use
     * @param player_trying_to_join is the player that has tried to join the game
     * @throws RemoteException;
     */
    void failedJoinInvalidNickname(Player player_trying_to_join) throws RemoteException;


    /**
     * This method is used to notify the client  that the provided game ID does not exist
     * @param game_id game's id
     * @throws RemoteException;
     */

    void invalidGameId(int game_id) throws RemoteException;

    /**
     * This method is used to is a generic error that occur when a player is trying to enter the game
     * @param why reason of the error
     * @throws RemoteException if a remote communication error occurs
     */
    void genericErrorWhenEnteringGame(String why) throws RemoteException; //



    /**
     * This method is used to indicate that the player is prepared to commence the game
     * @param game_model is the game model interface
     * @param nickname is the nickname of the player who is ready to start
     * @throws IOException;
     */
    void playerReadyForStarting(GameModelImmutable game_model, String nickname) throws IOException;


    //------------------------------------------------------------------------------------------------------------
    /**
     * This method is used to notify the client that the common objective cards have been extracted in the game.
     *
     * @param gameModel The immutable version of the game model
     * @throws RemoteException in case of a remote communication error.
     */
    void commonObjectiveCardsExtracted(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that a resource cards have been
     * extracted from the deck and add that card to his hand.
     *
     * @param gameModel The immutable version of the game model
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromDeck(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that a resource card have been
     * extracted from the table and add that card to his hand.
     *
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromTable(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that a gold card have been
     * extracted from the deck and add that card to his hand.
     *
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromDeck(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that a gold card have been
     * extracted from the table and add that card to his hand.
     *
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromTable(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract an objective card
     * from an empty deck.
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void objectiveCardExtractedFromEmptyDeck(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a resource card
     * from an empty deck.
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromEmptyDeck(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a resource card
     * from an empty table.
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void resourceCardExtractedFromEmptyTable(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a gold card
     * from an empty deck.
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromEmptyDeck(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that he has tried to extract a gold card
     * from an empty table.
     * @param gameModel The immutable version of the game model.
     * @throws RemoteException If a remote communication error occurs.
     */
    void goldCardExtractedFromEmptyTable(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client that a card has been placed on a player's personal board.
     * @param gameModel The immutable version of the game model
     * @throws RemoteException If a remote communication error occurs
     */
    void cardPlacedOnPersonalBoard(GameModelImmutable gameModel) throws RemoteException;

    /**
     * This method is employed to inform the client that the game has started
     * @param game_model is the game model interface
     * @throws RemoteException;
     */
    void gameStarted(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is employed to inform the client that the game is finished
     * @param game_model is the game model interface
     * @throws RemoteException;
     */
    void gameEnded(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is employed to inform that a message has been sent
     * @param game_model is the game model interface
     * @param msg is the message that has been sent
     * @throws RemoteException If a remote communication error occurs
     */
    void sentMessage(GameModelImmutable game_model, Message msg) throws RemoteException;


    /**
     * This method is employed to inform that a player has disconnected
     * @param gameModel is the game model interface
     * @param nickname player's nickname who has disconnected
     * @throws RemoteException if a remote communication error occurs
     */
    void playerDisconnected(GameModelImmutable gameModel, String nickname) throws RemoteException;

    /**
     * This method is used to notify that the next turn triggered
     * @param game_model is the game model interface
     * @throws RemoteException If access to the reference was not possible
     */
    void nextTurn(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify the client that a player has moved on the common board.
     * @param game_model The immutable version of the game model
     * @throws RemoteException If a remote communication error occurs
     */
    void playerHasMovedOnCommonBoard(GameModelImmutable game_model) throws RemoteException;


    /**
     * This method is used to inform that only one player is currently connected
     * @param gameModel is the game model
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     * @throws RemoteException if the reference is inaccessible
     */
    void onlyOnePlayerConnected(GameModelImmutable gameModel,
                                int secondsToWaitUntilGameEnded) throws RemoteException;

    /**
     * This method is used to notify that the last turn has started
     * @param game_model is the game model interface
     * @throws RemoteException if the reference inaccessible
     */
    void lastRound(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify that the second last round has started
     * It is the moment when a player reaches 20 points
     * @param game_model is the game model interface
     * @throws RemoteException if the reference inaccessible
     */
    void secondLastRound(GameModelImmutable game_model) throws RemoteException;

    /**
     * This method is used to notify that the game id selected does not exist
     * @param gameId is the game id
     * @throws RemoteException if the reference inaccessible
     */
    void gameIdNotExists(int gameId) throws RemoteException;

    // todo: check if these method are needed

    void newGame() throws RemoteException;

    void updatedGame() throws RemoteException;

    void removedGame() throws RemoteException;

    void playerJoinedGame() throws RemoteException;

    void gameIsFull() throws RemoteException;

    void modelChanged() throws RemoteException;

    void gameEnded();
}
