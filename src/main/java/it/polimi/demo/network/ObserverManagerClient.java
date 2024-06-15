package it.polimi.demo.network;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.dynamic.GameDynamic;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class implements the Listener interface to receive notifications of game events from the server.
 * It forwards these notifications to an associated GameDynamic instance or other components for handling.
 */
public class ObserverManagerClient implements Listener, Serializable {

    @Serial
    private static final long serialVersionUID = -5070962929563880709L;
    private GameDynamic dynamic;

    /**
     * Constructs an ObserverManagerClient with the specified GameDynamic instance.
     * @param dyn The GameDynamic instance or other handler associated with this observer client.
     */
    public ObserverManagerClient(GameDynamic dyn) {
        this.dynamic = dyn;
    }

    /**
     * Notifies the handler that a starter card has been placed.
     * @param model The current state of the game.
     * @param orientation The orientation in which the card is placed.
     * @param nick The nickname of the player who placed the card.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) throws RemoteException {
        dynamic.starterCardPlaced(model, orientation, nick);
    }

    /**
     * Notifies the handler that a card has been chosen.
     * @param model The current state of the game.
     * @param which_card The index of the chosen card.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void cardChosen(ModelView model, int which_card) throws RemoteException {
        dynamic.cardChosen(model, which_card);
    }

    /**
     * Notifies the handler that a card has been placed on the game board.
     * @param model The current state of the game.
     * @param where_to_place_x The x-coordinate where the card is placed.
     * @param where_to_place_y The y-coordinate where the card is placed.
     * @param orientation The orientation in which the card is placed.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        dynamic.cardPlaced(model, where_to_place_x, where_to_place_y, orientation);
    }

    /**
     * Notifies the handler that an illegal move was attempted.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void illegalMove(ModelView model) throws RemoteException {
        dynamic.illegalMove(model);
    }

    /**
     * Notifies the handler that a move was successfully executed.
     * @param model The current state of the game.
     * @param coord The coordinate where the successful move was made.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void successfulMove(ModelView model, Coordinate coord) throws RemoteException {
        dynamic.successfulMove(model, coord);
    }

    /**
     * Notifies the handler of an illegal move due to a specific reason.
     * @param model The current state of the game.
     * @param reason_why The reason for the illegal move.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) throws RemoteException {
        dynamic.illegalMoveBecauseOf(model, reason_why);
    }

    /**
     * Notifies the handler that a card has been drawn.
     * @param model The current state of the game.
     * @param index The index of the drawn card.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void cardDrawn(ModelView model, int index) throws RemoteException {
        dynamic.cardDrawn(model, index);
    }

    /**
     * Notifies the handler that a player has joined the game.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void playerJoined(ModelView model) throws RemoteException {
        dynamic.playerJoined(model);
    }

    /**
     * Notifies the handler that a player has left the game.
     * @param model The current state of the game.
     * @param nick The nickname of the player who left.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void playerLeft(ModelView model, String nick) throws RemoteException {
        dynamic.playerLeft(model, nick);
    }

    /**
     * Notifies the handler that a player couldn't join because the game is full.
     * @param wantedToJoin The player who attempted to join.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView model) throws RemoteException {
        dynamic.joinUnableGameFull(wantedToJoin, model);
    }

    /**
     * Notifies the handler that a player couldn't join because the nickname is already in use.
     * @param wantedToJoin The player who attempted to join.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        dynamic.joinUnableNicknameAlreadyIn(wantedToJoin);
    }

    /**
     * Notifies the handler that the specified game ID does not exist.
     * @param gameid The ID of the game that does not exist.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        dynamic.gameIdNotExists(gameid);
    }

    /**
     * Notifies the handler of a generic error when entering the game.
     * @param why The reason for the generic error.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        dynamic.genericErrorWhenEnteringGame(why);
    }

    /**
     * Notifies the handler that a player is ready to start the game.
     * @param model The current state of the game.
     * @param nick The nickname of the player who is ready.
     * @throws IOException If there is an I/O error.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void playerIsReadyToStart(ModelView model, String nick) throws IOException {
        dynamic.playerIsReadyToStart(model, nick);
    }

    /**
     * Notifies the handler that the game has started.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void gameStarted(ModelView model) throws RemoteException {
        dynamic.gameStarted(model);
    }

    /**
     * Notifies the handler that the game has ended.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void gameEnded(ModelView model) throws RemoteException {
        dynamic.gameEnded(model);
    }

    /**
     * Notifies the handler that the next turn is starting.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void nextTurn(ModelView model) throws RemoteException {
        dynamic.nextTurn(model);
    }

    /**
     * Notifies the handler that a player has disconnected from the game.
     * @param model The current state of the game.
     * @param nick The nickname of the player who disconnected.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void playerDisconnected(ModelView model, String nick) throws RemoteException {
        dynamic.playerDisconnected(model, nick);
    }

    /**
     * Notifies the handler to show the personal board of another player.
     * @param model The current state of the game.
     * @param playerNickname The nickname of the player whose board to show.
     * @param playerIndex The index of the player whose board to show.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void showOthersPersonalBoard(ModelView model, String playerNickname, int playerIndex) throws RemoteException {
        dynamic.showOthersPersonalBoard(model, playerNickname, playerIndex);
    }

    /**
     * Notifies the handler that the game is entering its second last round.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void secondLastRound(ModelView model) throws RemoteException {
        dynamic.secondLastRound(model);
    }

    /**
     * Notifies the handler that the game is entering its last round.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void lastRound(ModelView model) throws RemoteException {
        dynamic.lastRound(model);
    }

    /**
     * Notifies the handler that the game a message has been sent in the chat.
     * @param model The current state of the game.
     * @throws RemoteException If there is a remote communication error.
     */
    @Override
    public void messageSent(ModelView model, String nickname, Message message) throws RemoteException {
        dynamic.messageSent(model, nickname, message);
    }
}


