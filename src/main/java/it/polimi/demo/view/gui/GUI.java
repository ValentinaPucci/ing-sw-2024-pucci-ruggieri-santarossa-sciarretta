package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.view.flow.UI;
import it.polimi.demo.view.flow.utilities.GuiReader;
import it.polimi.demo.view.gui.controllers.LobbyController;
import it.polimi.demo.view.gui.controllers.RunningController;
import it.polimi.demo.view.gui.scene.SceneType;
import javafx.application.Platform;

import java.util.ArrayList;

import static it.polimi.demo.view.text.PrintAsync.printAsync;

public class GUI extends UI {

    private ApplicationGUI guiApplication;
    private GuiReader GuiReader;
    private boolean alreadyShowedPublisher = false; //to delete in tui
    private boolean alreadyShowedLobby = false;

    private String nickname;

    public GUI(ApplicationGUI guiApplication, GuiReader GuiReader) {
        this.guiApplication = guiApplication;
        this.GuiReader = GuiReader;
        //System.out.println("GUI constructor: "+ this.inputReaderGUI);
        nickname = null;
        init();
    }

    @Override
    public void init() {
        importantEvents = new ArrayList<>();
    }

    public void callPlatformRunLater(Runnable r) {
        //Need to use this method to call any methods inside the GuiApplication
        //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
        Platform.runLater(r);
    }

    @Override
    protected void show_menuOptions() {
        if (alreadyShowedPublisher) {
            callPlatformRunLater(() -> this.guiApplication.setInputReaderGUItoAllControllers(this.GuiReader));//So the controllers can add text to the buffer for the gameflow
            callPlatformRunLater(() -> this.guiApplication.createNewWindowWithStyle());
            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MENU));
        }
    }



    @Override
    protected void show_creatingNewGameMsg(String nickname) {
    }

    @Override
    protected void show_joiningFirstAvailableMsg(String nickname) {
    }

    @Override
    protected void show_joiningToGameIdMsg(int idGame, String nickname) {
        show_inputGameIdMsg();
    }

    @Override
    protected void show_inputGameIdMsg() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ID_GAME));
    }

    @Override
    protected void show_insertNicknameMsg() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NICKNAME));
    }

    @Override
    protected void show_insertNumOfPlayersMsg() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NUM_PLAYERS));
    }

    /**
     * This method show that the game has started
     *
     * @param model model where the game has started
     */
    @Override
    protected void show_gameStarted(ModelView model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.RUNNING));
        callPlatformRunLater(() -> this.guiApplication.showRunningModel(model, nickname));
    }

    @Override
    protected void show_chosenNickname(String nickname) {
        this.nickname = nickname;
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.LOBBY));
    }

    @Override
    protected void show_nextTurn(ModelView model, String nickname) {
        if (!alreadyShowedLobby) {
            show_gameStarted(model);
            alreadyShowedLobby = true;
        }
        callPlatformRunLater(() -> this.guiApplication.changeTurn(model, nickname));
    }

    //TODO x vale: reimplement
//    @Override
//    protected void show_updateCommonCards() {
//
//    }


    /**
     * this method show that the player is ready to start
     *
     * @param gameModel     model where events happen
     * @param nickname player's nickname
     */
    @Override
    protected void show_ReadyToStart(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.disableBtnReadyToStart());
    }

    /**
     * This method show that there are no available games to join
     *
     * @param msgToVisualize message that needs visualisation
     */
    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
        callPlatformRunLater(() -> this.guiApplication.showError(msgToVisualize));
    }

    @Override
    protected void show_gameEnded(ModelView model) {

    }

    @Override
    protected void show_playerJoined(ModelView gameModel, String nick) {
        if (!alreadyShowedLobby) {
            this.nickname = nick;
            callPlatformRunLater(() -> ((LobbyController) this.guiApplication.getController(SceneType.LOBBY)).setNicknameLabel(nick));
            callPlatformRunLater(() -> ((LobbyController) this.guiApplication.getController(SceneType.LOBBY)).setGameId(gameModel.getGameId()));

            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.LOBBY));
            callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
            alreadyShowedLobby = true;
        } else {
            //The player is in lobby and another player has joined
            callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
        }
    }

    @Override
    protected void show_starterCards(ModelView gameModel) {

    }

    @Override
    protected void show_objectiveCards(ModelView gameModel) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableObjectiveCardsClick());

    }

    @Override
    protected void show_personalBoard(String nick, ModelView gameModel) {

    }

    @Override
    protected void show_commonBoard(ModelView gameModel) {
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getController(SceneType.RUNNING)).setCommonCards(gameModel));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getController(SceneType.RUNNING)).setScoreBoardPosition(gameModel));
    }

    @Override
    protected void show_myTurnIsFinished() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).myTurnIsFinished());
    }

    @Override
    protected void show_playerHand(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).setCardHand(gameModel, nickname));
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableCommonCardsClick());
    }

    @Override
    protected void show_personalObjectiveCard(ModelView gameModel) {

    }

    @Override
    protected void show_cardChosen(String nickname, ModelView model) {

    }

    @Override
    public void show_illegalMove() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).illegalMove());
    }

    @Override
    protected void show_illegalMoveBecauseOf(String message) {

    }

    //TODO: reimplement vale
//    @Override
//    protected void show_successfulMove() {
//        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).successfulMove());
//    }

    @Override
    protected void show_whereToDrawFrom() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableCommonCardsClick());
    }

    @Override
    protected void show_commonObjectives(ModelView gameModel) {

    }

    @Override
    protected void show_messageSent(ModelView model, String nickname) {

    }

    @Override
    public void show_whichObjectiveToChooseMsg() {

    }

    @Override
    public void show_whichCardToPlaceMsg() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).whichCardToPlace());
    }


    @Override
    protected void show_NaNMsg() {

    }

    @Override
    protected void show_returnToMenuMsg() {

    }

    @Override
    protected void show_orientation(String message) {
        if(message.equals("Choose the orientation of the card to place")) {
            //System.out.println("GUI: whichOrientationToPlace()");
            callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).whichOrientationToPlace());
        }
        else if(message.equals("Choose the orientation of the starter card")){
            callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableStarterCardClick());
        }

    }

    @Override
    protected void show_genericMessage(String s) {
    }

    @Override
    protected void show_genericError(String s) {

    }



    /**
     * This method add an important event to the list of important events, and show it
     * @param input the string of the important event to add
     */
    @Override
    public void addImportantEvent(String input) {
        importantEvents.add(input);
        callPlatformRunLater(() -> this.guiApplication.showImportantEvents(this.importantEvents));
    }

    /**
     * This method reset the important events
     */
    @Override
    protected void resetImportantEvents() {
        this.importantEvents = new ArrayList<>();
        this.nickname = null;
        alreadyShowedPublisher = true;
        alreadyShowedLobby = false;
    }

    /**
     * This method show a message about a no connection error
     */
    @Override
    protected void show_noConnectionError() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
        callPlatformRunLater(() -> this.guiApplication.showError("Connection to server lost!"));
    }



}
