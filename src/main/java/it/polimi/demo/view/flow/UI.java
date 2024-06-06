package it.polimi.demo.view.flow;

import it.polimi.demo.model.ModelView;

import java.util.List;

/**
 * This class is father to both the TUI and GUI implementation<br>
 * it implements methods that everyone needs to have<br>
 */
public abstract class UI {

    protected List<String> importantEvents; //gameFacts that needs to be showed always in screen

    /**
     * Initialises GUI or TUI
     */
    public abstract void init();

    //----------------------
    //SHOW
    //----------------------

    /**
     * Shows menu options
     */
    protected abstract void show_menuOptions();

    /**
     * Shows the creating new game message
     *
     * @param nickname player's nickname
     */
    protected abstract void show_creatingNewGameMsg(String nickname);

    /**
     * Shows the join first available game message
     *
     * @param nickname player's nickname
     */
    protected abstract void show_joiningFirstAvailableMsg(String nickname);

    /**
     * Shows the join to specific game message
     *
     * @param idGame   id of the game the player is trying to join
     * @param nickname player's nickname
     */
    protected abstract void show_joiningToGameIdMsg(int idGame, String nickname);

    /**
     * Message that asks to insert specific game id
     */
    protected abstract void show_inputGameIdMsg();

    /**
     * Asks the player for his nickname
     */
    protected abstract void show_insertNicknameMsg();

    /**
     * Message that asks to pick up tiles
     *
     */
    protected abstract void show_insertNumOfPlayersMsg();

    /**
     * Shows game started message
     *
     * @param model model where the game has started
     */
    protected abstract void show_gameStarted(ModelView model);

    protected abstract void show_ReadyToStart(ModelView gameModel, String nicknameofyou);

    /**
     * Shows error message when there are no games available for joining
     *
     * @param msgToVisualize message that needs visualisation
     */
    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);

    /**
     * Shows the game ended message
     *
     * @param model where the game is ended
     */
    protected abstract void show_gameEnded(ModelView model);

    /**
     * Shows the players that have joined
     *
     * @param gameModel model where gameFacts happen
     * @param nick      player's nickname
     */
    protected abstract void show_playerJoined(ModelView gameModel, String nick);

    protected abstract void show_starterCards(ModelView gameModel);

    protected abstract void show_objectiveCards(ModelView gameModel);

    protected abstract void show_personalBoard(String nick, ModelView gameModel);

    protected abstract void show_commonBoard(ModelView gameModel);

    protected abstract void show_myTurnIsFinished();

    /**
     * Message that shows the player's hand
     *
     * @param gameModel the model that has the player hand that needs to be shown
     */
    protected abstract void show_playerHand(ModelView gameModel);

    protected abstract void show_personalObjectiveCard(ModelView gameModel);

    /**
     * Shows player's grabbed tiles
     *
     * @param nickname the player that grabbed the tiles
     * @param model    the model in which the player grabbed the tiles
     */
    protected abstract void show_cardChosen(String nickname, ModelView model);

    protected abstract void show_illegalMove();

    protected abstract void show_illegalMoveBecauseOf(String message);

    protected abstract void show_whereToDrawFrom();

    /**
     * Shows common cards extracted
     *
     * @param gameModel the model that has the common cards to show
     */
    protected abstract void show_commonObjectives(ModelView gameModel);

    /**
     * Shows the message that has been sent
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    protected abstract void show_messageSent(ModelView model, String nickname);


    public abstract void show_whichObjectiveToChooseMsg();

    /**
     * Message that asks to pick a tile to place
     */
    public abstract void show_whichCardToPlaceMsg();

    /**
     * Shows generic error message
     */
    protected abstract void show_NaNMsg();

    /**
     * Shows the message that asks to return to the main menu
     */
    protected abstract void show_returnToMenuMsg();

    /**
     * Shows the message that asks for direction to be chosen
     */
    protected abstract void show_orientation(String message);

    protected abstract void show_genericMessage(String s);

    protected abstract void show_genericError(String s);

    //----------------------
    //ACTIONS
    //----------------------

    /**
     * Shows message on important event added
     * @param input the string of the important event to add
     */
    public abstract void addImportantEvent(String input);

    /**
     * Resets the important gameFacts
     */
    protected abstract void resetImportantEvents();

    /**
     * Shows an error when there's no connection
     */
    protected abstract void show_noConnectionError();


    protected abstract void show_chosenNickname(String nickname);

    protected abstract void show_nextTurn(ModelView model, String nickname);
}
