package it.polimi.demo.view.gui;

import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.view.flow.UI;
import it.polimi.demo.view.flow.utilities.inputReaderGUI;
import it.polimi.demo.view.gui.controllers.LobbyController;
import it.polimi.demo.view.gui.controllers.RunningController;
import it.polimi.demo.view.gui.scene.SceneType;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.ArrayList;

public class GUI extends UI {

    private ApplicationGUI guiApplication;
    private inputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher = false; //to delete in tui
    private boolean alreadyShowedLobby = false;

    private String nickname;

    public GUI(ApplicationGUI guiApplication, inputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI = inputReaderGUI;
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

    /**
     * The show method is used to show the GUI, and set the active scene to the publisher.
     */
    @Override
    protected void show_publisher() {
        alreadyShowedPublisher = true;
    }

    @Override
    protected void show_menuOptions() {
        if (alreadyShowedPublisher) {
            callPlatformRunLater(() -> this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI));//So the controllers can add text to the buffer for the gameflow
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
    protected void show_gameStarted(GameModelImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.RUNNING));
        callPlatformRunLater(() -> this.guiApplication.showRunningModel(model, nickname));
    }

    @Override
    protected void show_chosenNickname(String nickname) {
        this.nickname = nickname;
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.LOBBY));
    }

    @Override
    protected void show_nextTurn(GameModelImmutable model, String nickname) {
        if (!alreadyShowedLobby) {
            show_gameStarted(model);
            alreadyShowedLobby = true;
        }
        callPlatformRunLater(() -> this.guiApplication.changeTurn(model, nickname));
    }


    /**
     * this method show that the player is ready to start
     *
     * @param gameModel     model where events happen
     * @param nickname player's nickname
     */
    @Override
    protected void show_ReadyToStart(GameModelImmutable gameModel, String nickname) {
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
    protected void show_gameEnded(GameModelImmutable model) {

    }

    @Override
    protected void show_playerJoined(GameModelImmutable gameModel, String nick) {
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
    protected void show_starterCards(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_objectiveCards(GameModelImmutable gameModel) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableObjectiveCardsClick());

    }

    @Override
    protected void show_personalBoard(String nick, GameModelImmutable gameModel) {

    }

    @Override
    protected void show_commonBoard(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_myTurnIsFinished() {

    }

    @Override
    protected void show_playerHand(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_personalObjectiveCard(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_cardChosen(String nickname, GameModelImmutable model) {

    }

    @Override
    protected void show_illegalMove() {

    }

    @Override
    protected void show_illegalMoveBecauseOf(String message) {

    }

    @Override
    protected void show_whereToDrawFrom() {

    }

    @Override
    protected void show_commonObjectives(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_messageSent(GameModelImmutable model, String nickname) {

    }

    @Override
    public void show_whichObjectiveToChooseMsg() {

    }

    @Override
    public void show_whichCardToPlaceMsg() {

    }

    @Override
    protected void show_NaNMsg() {

    }

    @Override
    protected void show_returnToMenuMsg() {

    }

    @Override
    protected void show_orientation(String message) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableStarterCardClick());
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
