package it.polimi.demo.network;

import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.network.utils.AuxFI;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class manages observer notifications from the game model and forwards them to the dynamic game view.
 * It implements the Listener interface and handles events by invoking corresponding methods on a GameDynamic instance.
 */
public class ObserverManagerClient implements Listener, Serializable {

    private static final long serialVersionUID = -5070962929563880709L;
    private transient GameDynamic dynamic;

    public ObserverManagerClient(GameDynamic dyn) {
        this.dynamic = dyn;
    }

    /**
     * Notifies that a starter card has been placed.
     * @param model the current model view
     * @param orientation the orientation of the starter card
     * @param nick the nickname of the player
     */
    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) {
        AuxFI.handleEvent(dynamic::starterCardPlaced, model, orientation, nick);
    }

    /**
     * Notifies that a card has been chosen.
     * @param model the current model view
     * @param which_card the index of the chosen card
     */
    @Override
    public void cardChosen(ModelView model, int which_card) {
        AuxFI.handleEvent(dynamic::cardChosen, model, which_card);
    }

    /**
     * Notifies that a card has been placed.
     * @param model the current model view
     * @param where_to_place_x the x-coordinate where the card is placed
     * @param where_to_place_y the y-coordinate where the card is placed
     * @param orientation the orientation of the placed card
     */
    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) {
        AuxFI.handleEvent(dynamic::cardPlaced, model, where_to_place_x, where_to_place_y, orientation);
    }

    /**
     * Notifies that an illegal move has been made.
     * @param model the current model view
     */
    @Override
    public void illegalMove(ModelView model) {
        AuxFI.handleEvent(dynamic::illegalMove, model);
    }

    /**
     * Notifies that a successful move has been made.
     * @param model the current model view
     * @param coord the coordinates of the successful move
     */
    @Override
    public void successfulMove(ModelView model, Coordinate coord) {
        AuxFI.handleEvent(dynamic::successfulMove, model, coord);
    }

    /**
     * Notifies that an illegal move has been made with a reason.
     * @param model the current model view
     * @param reason_why the reason why the move is illegal
     */
    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) {
        AuxFI.handleEvent(dynamic::illegalMoveBecauseOf, model, reason_why);
    }

    /**
     * Notifies that a card has been drawn.
     * @param model the current model view
     * @param index the index of the drawn card
     */
    @Override
    public void cardDrawn(ModelView model, int index) {
        AuxFI.handleEvent(dynamic::cardDrawn, model, index);
    }

    /**
     * Notifies that a player has joined the game.
     * @param model the current model view
     */
    @Override
    public void playerJoined(ModelView model) {
        AuxFI.handleEvent(dynamic::playerJoined, model);
    }

    /**
     * Notifies that a player has left the game.
     * @param model the current model view
     * @param nick the nickname of the player who left
     */
    @Override
    public void playerLeft(ModelView model, String nick)  {
        AuxFI.handleEvent(dynamic::playerLeft, model, nick);
    }

    /**
     * Notifies that a player is unable to join because the game is full.
     * @param wantedToJoin the player who wanted to join
     * @param model the current model view
     */
    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView model)  {
        AuxFI.handleEvent(dynamic::joinUnableGameFull, wantedToJoin, model);
    }

    /**
     * Notifies that a player is unable to join because the nickname is already in use.
     * @param wantedToJoin the player who wanted to join
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin)  {
        AuxFI.handleEvent(dynamic::joinUnableNicknameAlreadyIn, wantedToJoin);
    }

    /**
     * Notifies that a game with the specified ID does not exist.
     * @param game_id the ID of the game
     */
    @Override
    public void gameIdNotExists(int game_id)  {
        AuxFI.handleEvent(dynamic::gameIdNotExists, game_id);
    }

    /**
     * Notifies of a generic error when entering a game.
     * @param why the reason for the error
     */
    @Override
    public void genericErrorWhenEnteringGame(String why)  {
        AuxFI.handleEvent(dynamic::genericErrorWhenEnteringGame, why);
    }

    /**
     * Notifies that a player is ready to start the game.
     * @param model the current model view
     * @param nick the nickname of the player who is ready
     */
    @Override
    public void playerIsReadyToStart(ModelView model, String nick) throws IOException, RemoteException {
        AuxFI.handleEvent(dynamic::playerIsReadyToStart, model, nick);
    }

    /**
     * Notifies that the game has started.
     * @param model the current model view
     */
    @Override
    public void gameStarted(ModelView model)  {
        AuxFI.handleEvent(dynamic::gameStarted, model);
    }

    /**
     * Notifies that the game has ended.
     * @param model the current model view
     */
    @Override
    public void gameEnded(ModelView model)  {
        AuxFI.handleEvent(dynamic::gameEnded, model);
    }

    /**
     * Notifies that it is the next player's turn.
     * @param model the current model view
     */
    @Override
    public void nextTurn(ModelView model)  {
        AuxFI.handleEvent(dynamic::nextTurn, model);
    }

    /**
     * Notifies that a player has disconnected.
     * @param model the current model view
     * @param nick the nickname of the disconnected player
     */
    @Override
    public void playerDisconnected(ModelView model, String nick)  {
        AuxFI.handleEvent(dynamic::playerDisconnected, model, nick);
    }

    /**
     * Shows another player's personal board.
     * @param model the current model view
     * @param playerNickname the nickname of the player whose board is shown
     * @param playerIndex the index of the player
     */
    @Override
    public void showOthersPersonalBoard(ModelView model, String playerNickname, int playerIndex)  {
        AuxFI.handleEvent(dynamic::showOthersPersonalBoard, model, playerNickname, playerIndex);
    }

    /**
     * Notifies that the second last round has started.
     * @param model the current model view
     */
    @Override
    public void secondLastRound(ModelView model)  {
        AuxFI.handleEvent(dynamic::secondLastRound, model);
    }

    /**
     * Notifies that the last round has started.
     * @param model the current model view
     */
    @Override
    public void lastRound(ModelView model)  {
        AuxFI.handleEvent(dynamic::lastRound, model);
    }

    /**
     * Notifies that a message has been sent.
     * @param model the current model view
     * @param nickname the nickname of the sender
     * @param message the message that was sent
     */
    @Override
    public void messageSent(ModelView model, String nickname, Message message)  {
        AuxFI.handleEvent(dynamic::messageSent, model, nickname, message);
    }
}
