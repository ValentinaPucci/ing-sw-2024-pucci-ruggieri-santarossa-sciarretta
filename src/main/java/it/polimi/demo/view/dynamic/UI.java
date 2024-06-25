package it.polimi.demo.view.dynamic;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Coordinate;

/**
 * Abstract class defining the interface for the User Interface (ui) of the application.
 */
public abstract class UI {

    /**
     * Shows the options available to the user.
     */
    protected abstract void startTheGame();

    /**
     * Shows the message indicating the creation of a game with the specified nickname.
     * @param nickname The nickname of the player who created the game.
     */
    protected abstract void displayCreateGame(String nickname);

    /**
     * Shows the message indicating the joining of a random game by the specified nickname.
     * @param nickname The nickname of the player who joined the game.
     */
    protected abstract void displayJoinRandom(String nickname);

    /**
     * Shows the message indicating the joining of a specific game by the specified nickname.
     * @param idGame The ID of the game.
     * @param nickname The nickname of the player who joined the game.
     */
    protected abstract void displayJoin(int idGame, String nickname);

    /**
     * Shows a prompt to insert the game ID.
     */
    protected abstract void displayInsertGameId();

    /**
     * Shows a prompt to insert the nickname.
     */
    protected abstract void displayInsertNickname();

    /**
     * Shows a prompt to insert the number of players.
     */
    protected abstract void displayInsertNumOfPlayers();

    /**
     * Shows the message indicating the game has started.
     * @param model The model view containing game state information.
     */
    protected abstract void displayGameStarted(ModelView model);

    /**
     * Shows a message indicating that the player is ready to start.
     * @param gameModel The model view containing game state information.
     * @param nicknameofyou The nickname of the player who is ready to start.
     */
    protected abstract void displayReadyToStart(ModelView gameModel, String nicknameofyou);

    /**
     * Shows the message indicating the game has ended.
     * @param model The model view containing game state information.
     */
    protected abstract void displayGameEnded(ModelView model);

    /**
     * Shows a message indicating that a player has joined the game.
     * @param gameModel The model view containing game state information.
     * @param nick The nickname of the player who joined the game.
     */
    protected abstract void displayPlayerJoined(ModelView gameModel, String nick);

    /**
     * Shows the starter cards of the game.
     * @param gameModel The model view containing game state information.
     */
    protected abstract void displayStarterCards(ModelView gameModel);

    /**
     * Shows the objective cards of the game.
     * @param gameModel The model view containing game state information.
     */
    protected abstract void displayObjectiveCards(ModelView gameModel);

    /**
     * Shows the personal board of a player.
     * @param nick The nickname of the player.
     * @param gameModel The model view containing game state information.
     */
    protected abstract void displayPersonalBoard(String nick, ModelView gameModel);

    /**
     * Shows the common board of the game.
     * @param gameModel The model view containing game state information.
     */
    protected abstract void displayCommonBoard(ModelView gameModel);

    /**
     * Show the correct position of players' pawns
     */

    protected abstract void displayPawnPositions(ModelView model);

    /**
     * Shows a message indicating the current player's turn has finished.
     */
    protected abstract void displayMyTurnIsFinished();

    /**
     * Shows the player's hand.
     * @param gameModel The model view containing game state information.
     * @param nickname The nickname of the player.
     */
    protected abstract void displayPlayerHand(ModelView gameModel, String nickname);

    /**
     * Shows the personal objective card of the player.
     * @param gameModel The model view containing game state information.
     */
    protected abstract void displayPersonalObjectiveCard(ModelView gameModel);

    /**
     * Shows a message indicating the card chosen by the player.
     * @param nickname The nickname of the player.
     * @param model The model view containing game state information.
     */
    protected abstract void displayCardChosen(String nickname, ModelView model);

    /**
     * Shows a message indicating an illegal move.
     */
    protected abstract void displayIllegalMove();

    /**
     * Shows a message indicating an illegal move due to a specific reason.
     * @param message The reason for the illegal move.
     */
    protected abstract void displayIllegalMoveBecauseOf(String message);

    /**
     * Shows a message indicating a successful move with the specified coordinates.
     * @param coord The coordinates of the corner that the user clicked.
     */
    protected abstract void displaySuccessfulMove(Coordinate coord);

    /**
     * Shows a prompt indicating where to draw a card from.
     */
    protected abstract void displayWhereToDrawFrom();

    /**
     * Shows a message indicating that a message was sent.
     * @param model The model view containing game state information.
     * @param nickname The nickname of the player who sent the message.
     */
    protected abstract void displayMessageSent(ModelView model, String nickname);

    /**
     * Shows a prompt to choose which objective card to choose.
     */
    protected abstract void displayWhichObjectiveToChoose();

    /**
     * Shows a prompt to choose which card to place.
     */
    protected abstract void displayWhichCardToPlace();

    /**
     * Shows a message indicating invalid input.
     */
    protected abstract void displayInvalidInput();

    /**
     * Shows the main menu.
     */
    protected abstract void displayMenu();

    /**
     * Shows a prompt with a specific message indicating orientation.
     * @param message The message indicating orientation.
     */
    protected abstract void displayOrientation(String message);

    /**
     * Shows a generic message.
     * @param s The message to show.
     */
    protected abstract void displayGenericMessage(String s);

    /**
     * Shows a generic error message.
     * @param s The error message to show.
     */
    protected abstract void displayGenericError(String s);

    /**
     * Shows a message indicating a no connection error.
     */
    protected abstract void displayNoConnectionError();

    /**
     * Shows the chosen nickname.
     * @param nickname The chosen nickname.
     */
    protected abstract void displayChosenNickname(String nickname);

    /**
     * Shows the next turn in the game.
     * @param model The model view containing game state information.
     * @param nickname The nickname of the player whose turn is next.
     */
    protected abstract void displayNextTurn(ModelView model, String nickname);

    /**
     * Shows a message indicating a card has been drawn.
     * @param model The model view containing game state information.
     * @param nickname The nickname of the player who drew the card.
     */
    protected abstract void displayCardDrawn(ModelView model, String nickname);

    /**
     * Shows the personal board of another player.
     * @param modelView The model view containing game state information.
     * @param playerIndex The index of the player whose personal board is to be shown.
     */
    protected abstract void displayOthersPersonalBoard(ModelView modelView, int playerIndex);

    /**
     * Shows the message indicating the player has left the game.
     * @param modelView The model view containing game state information.
     * @param nick The nickname of the player who left the game.
     */
    protected abstract void displayPlayerLeft(ModelView modelView, String nick);

}
