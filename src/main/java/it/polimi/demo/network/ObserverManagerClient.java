package it.polimi.demo.network;

import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.dynamic.GameDynamic;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class implements the Listener interface to receive notifications of game events from the server.
 * It forwards these notifications to an associated GameDynamic instance or other components for handling.
 */
public class ObserverManagerClient implements Listener, Serializable {

    private static final long serialVersionUID = -5070962929563880709L;
    private transient GameDynamic dynamic; // Use 'transient' to avoid serializing GameDynamic

    /**
     * Constructs an ObserverManagerClient with the specified GameDynamic instance.
     *
     * @param dyn The GameDynamic instance or other handler associated with this observer client.
     */
    public ObserverManagerClient(GameDynamic dyn) {
        this.dynamic = dyn;
    }

    /**
     * Handles the event of a starter card being placed.
     *
     * @param model       The current model view of the game
     * @param orientation The orientation of the placed card
     * @param nick        The nickname of the player who placed the starter card
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleStarterCardPlaced(ModelView model, Orientation orientation, String nick) throws RemoteException {
        handleEvent(() -> dynamic.starterCardPlaced(model, orientation, nick));
    }

    /**
     * Handles the event of a card being chosen.
     *
     * @param model      The current model view of the game
     * @param which_card The index of the chosen card
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleCardChosen(ModelView model, int which_card) throws RemoteException {
        handleEvent(() -> dynamic.cardChosen(model, which_card));
    }

    /**
     * Handles the event of a card being placed on the game board.
     *
     * @param model       The current model view of the game
     * @param x           The x-coordinate of the placement
     * @param y           The y-coordinate of the placement
     * @param orientation The orientation of the placed card
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleCardPlaced(ModelView model, int x, int y, Orientation orientation) throws RemoteException {
        handleEvent(() -> dynamic.cardPlaced(model, x, y, orientation));
    }

    /**
     * Handles the event of an illegal move attempted by a player.
     *
     * @param model The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleIllegalMove(ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.illegalMove(model));
    }

    /**
     * Handles the event of a successful move performed by a player.
     *
     * @param model The current model view of the game
     * @param coord The coordinate of the successful move
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleSuccessfulMove(ModelView model, Coordinate coord) throws RemoteException {
        handleEvent(() -> dynamic.successfulMove(model, coord));
    }

    /**
     * Handles the event of an illegal move due to a specific reason.
     *
     * @param model  The current model view of the game
     * @param reason The reason for the illegal move
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleIllegalMoveBecauseOf(ModelView model, String reason) throws RemoteException {
        handleEvent(() -> dynamic.illegalMoveBecauseOf(model, reason));
    }

    /**
     * Handles the event of a player drawing a card.
     *
     * @param model The current model view of the game
     * @param index The index of the drawn card
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleCardDrawn(ModelView model, int index) throws RemoteException {
        handleEvent(() -> dynamic.cardDrawn(model, index));
    }

    /**
     * Handles the event of a new player joining the game.
     *
     * @param model The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handlePlayerJoined(ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.playerJoined(model));
    }

    /**
     * Handles the event of a player leaving the game.
     *
     * @param model The current model view of the game
     * @param nick  The nickname of the player who left
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handlePlayerLeft(ModelView model, String nick) throws RemoteException {
        handleEvent(() -> dynamic.playerLeft(model, nick));
    }

    /**
     * Handles the event of a player trying to join but the game is full.
     *
     * @param wantedToJoin The player who wanted to join
     * @param model        The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleJoinUnableGameFull(Player wantedToJoin, ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.joinUnableGameFull(wantedToJoin, model));
    }

    /**
     * Handles the event of a player trying to join with a nickname that is already in use.
     *
     * @param wantedToJoin The player who wanted to join
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleJoinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        handleEvent(() -> dynamic.joinUnableNicknameAlreadyIn(wantedToJoin));
    }

    /**
     * Handles the event of attempting to enter a game with a non-existent ID.
     *
     * @param gameid The ID of the game that does not exist
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleGameIdNotExists(int gameid) throws RemoteException {
        handleEvent(() -> dynamic.gameIdNotExists(gameid));
    }

    /**
     * Handles a generic error when entering a game.
     *
     * @param why The reason for the error
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleGenericErrorWhenEnteringGame(String why) throws RemoteException {
        handleEvent(() -> dynamic.genericErrorWhenEnteringGame(why));
    }

    /**
     * Handles the event of a player being ready to start the game.
     *
     * @param model The current model view of the game
     * @param nick  The nickname of the player who is ready
     * @throws IOException     If there's an IO error
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handlePlayerIsReadyToStart(ModelView model, String nick) throws IOException, RemoteException {
        handleEvent(() -> dynamic.playerIsReadyToStart(model, nick));
    }

    /**
     * Handles the event of the game starting.
     *
     * @param model The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleGameStarted(ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.gameStarted(model));
    }

    /**
     * Handles the event of the game ending.
     *
     * @param model The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleGameEnded(ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.gameEnded(model));
    }

    /**
     * Handles the event of the next turn in the game.
     *
     * @param model The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleNextTurn(ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.nextTurn(model));
    }

    /**
     * Handles the event of a player disconnecting from the game.
     *
     * @param model The current model view of the game
     * @param nick  The nickname of the player who disconnected
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handlePlayerDisconnected(ModelView model, String nick) throws RemoteException {
        handleEvent(() -> dynamic.playerDisconnected(model, nick));
    }

    /**
     * Handles the event of showing another player's personal board.
     *
     * @param model          The current model view of the game
     * @param playerNickname The nickname of the player whose board is shown
     * @param playerIndex    The index of the player
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleShowOthersPersonalBoard(ModelView model, String playerNickname, int playerIndex) throws RemoteException {
        handleEvent(() -> dynamic.showOthersPersonalBoard(model, playerNickname, playerIndex));
    }

    /**
     * Handles the event of the second last round of the game.
     *
     * @param model The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleSecondLastRound(ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.secondLastRound(model));
    }

    /**
     * Handles the event of the last round of the game.
     *
     * @param model The current model view of the game
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleLastRound(ModelView model) throws RemoteException {
        handleEvent(() -> dynamic.lastRound(model));
    }

    /**
     * Handles the event of a message being sent in the game chat.
     *
     * @param model    The current model view of the game
     * @param nickname The nickname of the player sending the message
     * @param message  The message sent by the player
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleMessageSent(ModelView model, String nickname, Message message) throws RemoteException {
        handleEvent(() -> dynamic.messageSent(model, nickname, message));
    }

    /**
     * Helper method to handle exceptions that occur during event handling.
     *
     * @param action The action to be executed
     * @throws RemoteException If there's a problem with remote communication
     */
    private void handleEvent(RemoteEvent action) throws RemoteException {
        try {
            action.run();
        } catch (Exception e) {
            throw new RemoteException("Event handling failed", e);
        }
    }

    // Functional interface for remote events
    @FunctionalInterface
    interface RemoteEvent {
        void run() throws Exception;
    }

    // Implementations of Listener interface methods

    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) throws RemoteException {
        handleStarterCardPlaced(model, orientation, nick);
    }

    @Override
    public void cardChosen(ModelView model, int which_card) throws RemoteException {
        handleCardChosen(model, which_card);
    }

    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        handleCardPlaced(model, where_to_place_x, where_to_place_y, orientation);
    }

    @Override
    public void illegalMove(ModelView model) throws RemoteException {
        handleIllegalMove(model);
    }

    @Override
    public void successfulMove(ModelView model, Coordinate coord) throws RemoteException {
        handleSuccessfulMove(model, coord);
    }

    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) throws RemoteException {
        handleIllegalMoveBecauseOf(model, reason_why);
    }

    @Override
    public void cardDrawn(ModelView model, int index) throws RemoteException {
        handleCardDrawn(model, index);
    }

    @Override
    public void playerJoined(ModelView model) throws RemoteException {
        handlePlayerJoined(model);
    }

    @Override
    public void playerLeft(ModelView model, String nick) throws RemoteException {
        handlePlayerLeft(model, nick);
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView model) throws RemoteException {
        handleJoinUnableGameFull(wantedToJoin, model);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        handleJoinUnableNicknameAlreadyIn(wantedToJoin);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        handleGameIdNotExists(gameid);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        handleGenericErrorWhenEnteringGame(why);
    }

    @Override
    public void playerIsReadyToStart(ModelView model, String nick) throws IOException, RemoteException {
        handlePlayerIsReadyToStart(model, nick);
    }

    @Override
    public void gameStarted(ModelView model) throws RemoteException {
        handleGameStarted(model);
    }

    @Override
    public void gameEnded(ModelView model) throws RemoteException {
        handleGameEnded(model);
    }

    @Override
    public void nextTurn(ModelView model) throws RemoteException {
        handleNextTurn(model);
    }

    @Override
    public void playerDisconnected(ModelView model, String nick) throws RemoteException {
        handlePlayerDisconnected(model, nick);
    }

    @Override
    public void showOthersPersonalBoard(ModelView model, String playerNickname, int playerIndex) throws RemoteException {
        handleShowOthersPersonalBoard(model, playerNickname, playerIndex);
    }

    @Override
    public void secondLastRound(ModelView model) throws RemoteException {
        handleSecondLastRound(model);
    }

    @Override
    public void lastRound(ModelView model) throws RemoteException {
        handleLastRound(model);
    }

    @Override
    public void messageSent(ModelView model, String nickname, Message message) throws RemoteException {
        handleMessageSent(model, nickname, message);
    }
}