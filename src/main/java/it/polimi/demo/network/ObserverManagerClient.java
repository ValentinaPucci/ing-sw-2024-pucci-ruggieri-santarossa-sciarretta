package it.polimi.demo.network;

import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.network.utils.AuxFI;
import java.io.Serializable;


/**
 * This class manages observer notifications from the game model and forwards them to the dyn game view.
 * It implements the Listener interface and handles events by invoking corresponding methods on a GameDynamic instance.
 */
public class ObserverManagerClient implements Listener, Serializable {

    private static final long serialVersionUID = -5070962929563880709L;
    private transient GameDynamic dyn;

    public ObserverManagerClient(GameDynamic dyn) {
        this.dyn = dyn;
    }

    /**
     * Notifies that a starter card has been placed.
     * @param model the current model view
     * @param orientation the orientation of the starter card
     * @param nick the nickname of the player
     */
    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) {
        AuxFI.handleEvent(dyn::starterCardPlaced, model, orientation, nick);
    }

    /**
     * Notifies that a card has been chosen.
     * @param model the current model view
     * @param which_card the index of the chosen card
     */
    @Override
    public void cardChosen(ModelView model, int which_card) {
        AuxFI.handleEvent(dyn::cardChosen, model, which_card);
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
        AuxFI.handleEvent(dyn::cardPlaced, model, where_to_place_x, where_to_place_y, orientation);
    }

    /**
     * Notifies that an illegal move has been made.
     * @param model the current model view
     */
    @Override
    public void illegalMove(ModelView model) {
        AuxFI.handleEvent(dyn::illegalMove, model);
    }

    /**
     * Notifies that a successful move has been made.
     * @param model the current model view
     * @param coord the coordinates of the successful move
     */
    @Override
    public void successfulMove(ModelView model, Coordinate coord) {
        AuxFI.handleEvent(dyn::successfulMove, model, coord);
    }

    /**
     * Notifies that an illegal move has been made with a reason.
     * @param model the current model view
     * @param reason_why the reason why the move is illegal
     */
    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) {
        AuxFI.handleEvent(dyn::illegalMoveBecauseOf, model, reason_why);
    }

    /**
     * Notifies that a card has been drawn.
     * @param model the current model view
     * @param index the index of the drawn card
     */
    @Override
    public void cardDrawn(ModelView model, int index) {
        AuxFI.handleEvent(dyn::cardDrawn, model, index);
    }

    /**
     * Notifies that a player has joined the game.
     * @param model the current model view
     */
    @Override
    public void playerJoined(ModelView model) {
        AuxFI.handleEvent(dyn::playerJoined, model);
    }

    /**
     * Notifies that a player has left the game.
     * @param model the current model view
     * @param nick the nickname of the player who left
     */
    @Override
    public void playerLeft(ModelView model, String nick)  {
        AuxFI.handleEvent(dyn::playerLeft, model, nick);
    }

    /**
     * Notifies of a generic error when entering a game.
     * @param why the reason for the error
     */
    @Override
    public void genericErrorWhenEnteringGame(String why)  {
        AuxFI.handleEvent(dyn::genericErrorWhenEnteringGame, why);
    }

    /**
     * Notifies that a player is ready to start the game.
     * @param model the current model view
     * @param nick the nickname of the player who is ready
     */
    @Override
    public void playerIsReadyToStart(ModelView model, String nick) {
        AuxFI.handleEvent(dyn::playerIsReadyToStart, model, nick);
    }

    /**
     * Notifies that the game has started.
     * @param model the current model view
     */
    @Override
    public void gameStarted(ModelView model)  {
        AuxFI.handleEvent(dyn::gameStarted, model);
    }

    /**
     * Notifies that the game has ended.
     * @param model the current model view
     */
    @Override
    public void gameEnded(ModelView model)  {
        AuxFI.handleEvent(dyn::gameEnded, model);
    }

    /**
     * Notifies that it is the next player's turn.
     * @param model the current model view
     */
    @Override
    public void nextTurn(ModelView model)  {
        AuxFI.handleEvent(dyn::nextTurn, model);
    }

    /**
     * Notifies that a player has disconnected.
     * @param model the current model view
     * @param nick the nickname of the disconnected player
     */
    @Override
    public void playerDisconnected(ModelView model, String nick)  {
        AuxFI.handleEvent(dyn::playerDisconnected, model, nick);
    }

    /**
     * Shows another player's personal board.
     * @param model the current model view
     * @param playerNickname the nickname of the player whose board is shown
     * @param playerIndex the index of the player
     */
    @Override
    public void showOthersPersonalBoard(ModelView model, String playerNickname, int playerIndex)  {
        AuxFI.handleEvent(dyn::showOthersPersonalBoard, model, playerNickname, playerIndex);
    }

    /**
     * Notifies that the second last round has started.
     * @param model the current model view
     */
    @Override
    public void secondLastRound(ModelView model)  {
        AuxFI.handleEvent(dyn::secondLastRound, model);
    }

    /**
     * Notifies that the last round has started.
     * @param model the current model view
     */
    @Override
    public void lastRound(ModelView model)  {
        AuxFI.handleEvent(dyn::lastRound, model);
    }

    /**
     * Notifies that a message has been sent.
     * @param model the current model view
     * @param nickname the nickname of the sender
     * @param message the message that was sent
     */
    @Override
    public void messageSent(ModelView model, String nickname, Message message)  {
        AuxFI.handleEvent(dyn::messageSent, model, nickname, message);
    }
}
