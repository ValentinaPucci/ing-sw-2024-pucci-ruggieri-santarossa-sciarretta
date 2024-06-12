package it.polimi.demo.view.dynamic;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Coordinate;

import java.util.List;

public abstract class UI {

    protected List<String> importantEvents; //gameFacts that needs to be showed always in screen

    public abstract void init();

    // --------------------- show ---------------------

    protected abstract void show_menuOptions();

    protected abstract void show_creatingNewGameMsg(String nickname);

    protected abstract void show_joiningFirstAvailableMsg(String nickname);

    protected abstract void show_joiningToGameIdMsg(int idGame, String nickname);

    protected abstract void show_inputGameIdMsg();

    protected abstract void show_insertNicknameMsg();

    protected abstract void show_insertNumOfPlayersMsg();

    protected abstract void show_gameStarted(ModelView model);

    protected abstract void show_ReadyToStart(ModelView gameModel, String nicknameofyou);

    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);

    protected abstract void show_gameEnded(ModelView model);

    protected abstract void show_playerJoined(ModelView gameModel, String nick);

    protected abstract void show_starterCards(ModelView gameModel);

    protected abstract void show_objectiveCards(ModelView gameModel);

    protected abstract void show_personalBoard(String nick, ModelView gameModel);

    protected abstract void show_commonBoard(ModelView gameModel);

    protected abstract void show_myTurnIsFinished();

    protected abstract void show_playerHand(ModelView gameModel, String nickname);

    protected abstract void show_personalObjectiveCard(ModelView gameModel);

    protected abstract void show_cardChosen(String nickname, ModelView model);

    protected abstract void show_illegalMove();

    protected abstract void show_illegalMoveBecauseOf(String message);

    protected abstract void show_successfulMove(Coordinate coord);

    protected abstract void show_whereToDrawFrom();

    protected abstract void show_commonObjectives(ModelView gameModel);

    protected abstract void show_messageSent(ModelView model, String nickname);

    public abstract void show_whichObjectiveToChooseMsg();

    public abstract void show_whichCardToPlaceMsg();

    protected abstract void show_invalidInput();

    protected abstract void show_menu();

    protected abstract void show_orientation(String message);

    protected abstract void show_genericMessage(String s);

    protected abstract void show_genericError(String s);

    // --------------------- actions ---------------------

    public abstract void addImportantEvent(String input);

    protected abstract void resetImportantEvents();

    protected abstract void show_noConnectionError();

    protected abstract void show_chosenNickname(String nickname);

    protected abstract void show_nextTurn(ModelView model, String nickname);

    protected abstract void show_cardDrawn(ModelView model,String nickname);

    protected abstract void show_othersPersonalBoard(ModelView modelView, int playerIndex);
}
