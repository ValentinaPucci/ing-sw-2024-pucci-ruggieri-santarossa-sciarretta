package it.polimi.demo.observer;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for listeners observing game events.
 * Implementations of this interface will receive notifications
 * about various events occurring in the game.
 */
public interface Listener extends Remote {

    /**
     * Notifies that a starter card has been placed.
     * @param model The current state of the game.
     * @param orientation The orientation in which the card is placed.
     * @param nick The nickname of the player who placed the card.
     * @throws RemoteException If a remote communication error occurs.
     */
    void starterCardPlaced(ModelView model, Orientation orientation, String nick) throws RemoteException;

    /**
     * Notifies that a card has been chosen.
     * @param model The current state of the game.
     * @param which_card The index of the chosen card.
     * @throws RemoteException If a remote communication error occurs.
     */
    void cardChosen(ModelView model, int which_card) throws RemoteException;

    /**
     * Notifies that a card has been placed on the game board.
     * @param model The current state of the game.
     * @param where_to_place_x The x-coordinate where the card is placed.
     * @param where_to_place_y The y-coordinate where the card is placed.
     * @param orientation The orientation in which the card is placed.
     * @throws RemoteException If a remote communication error occurs.
     */
    void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException;

    /**
     * Notifies that an illegal move was attempted.
     * @param model The current state of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void illegalMove(ModelView model) throws RemoteException;

    /**
     * Notifies that a move was successfully executed.
     * @param model The current state of the game.
     * @param coord The coordinate where the successful move was made.
     * @throws RemoteException If a remote communication error occurs.
     */
    void successfulMove(ModelView model, Coordinate coord) throws RemoteException;

    /**
     * Notifies that an illegal move was attempted due to a specific reason.
     * @param model The current state of the game.
     * @param reason_why The reason why the move was illegal.
     * @throws RemoteException If a remote communication error occurs.
     */
    void illegalMoveBecauseOf(ModelView model, String reason_why) throws RemoteException;

    /**
     * Notifies that a card has been drawn.
     * @param model The current state of the game.
     * @param index The index of the drawn card.
     * @throws RemoteException If a remote communication error occurs.
     */
    void cardDrawn(ModelView model, int index) throws RemoteException;

    /**
     * Notifies that a player has joined the game.
     * @param model The current state of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void playerJoined(ModelView model) throws RemoteException;

    /**
     * Notifies that a player has left the game.
     * @param model The current state of the game.
     * @param nick The nickname of the player who left.
     * @throws RemoteException If a remote communication error occurs.
     */
    void playerLeft(ModelView model, String nick) throws RemoteException;

    /**
     * Notifies of a generic error when attempting to enter a game.
     * @param why The reason for the error.
     * @throws RemoteException If a remote communication error occurs.
     */
    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    /**
     * Notifies that a player is ready to start the game.
     * @param model The current state of the game.
     * @param nick The nickname of the player who is ready.
     * @throws IOException If an I/O error occurs.
     */
    void playerIsReadyToStart(ModelView model, String nick) throws IOException;

    /**
     * Notifies that the game has started.
     * @param model The current state of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void gameStarted(ModelView model) throws RemoteException;

    /**
     * Notifies that the game has ended.
     * @param model The current state of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void gameEnded(ModelView model) throws RemoteException;

    /**
     * Notifies that the second last round of the game is starting.
     * @param model The current state of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void secondLastRound(ModelView model) throws RemoteException;

    /**
     * Notifies that the last round of the game is starting.
     * @param model The current state of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void lastRound(ModelView model) throws RemoteException;

    /**
     * Notifies that a message was sent in the game chat.
     * @param model The current state of the game.
     * @param nickname The nickname of the player who sent the message.
     * @param message The message sent.
     * @throws RemoteException If a remote communication error occurs.
     */
    void messageSent(ModelView model, String nickname, Message message) throws RemoteException;

    /**
     * Notifies that it's the next player's turn.
     * @param model The current state of the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void nextTurn(ModelView model) throws RemoteException;

    /**
     * Notifies that a player has disconnected from the game.
     * @param model The current state of the game.
     * @param nick The nickname of the player who disconnected.
     * @throws RemoteException If a remote communication error occurs.
     */
    void playerDisconnected(ModelView model, String nick) throws RemoteException;

    /**
     * Notifies to show the personal board of another player.
     * @param modelView The current state of the game.
     * @param playerNickname The nickname of the player whose board to show.
     * @param playerIndex The index of the player in the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    void showOthersPersonalBoard(ModelView modelView, String playerNickname, int playerIndex) throws RemoteException;
}

