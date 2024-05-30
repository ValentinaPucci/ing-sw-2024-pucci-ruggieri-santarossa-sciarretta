package it.polimi.demo.view.gui;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.view.flow.UI;
import it.polimi.demo.view.flow.utilities.inputReaderGUI;
import it.polimi.demo.view.gui.scene.SceneType;
import javafx.application.Platform;

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

    }

    @Override
    protected void show_inputGameIdMsg() {

    }

    @Override
    protected void show_insertNicknameMsg() {

    }

    @Override
    protected void show_insertNumOfPlayersMsg() {

    }

    @Override
    protected void show_chosenNumOfPLayers(int n) {

    }

    @Override
    protected void show_chosenNickname(String nickname) {

    }

    @Override
    protected void show_gameStarted(GameModelImmutable model) {

    }

    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {

    }

    @Override
    protected void show_gameEnded(GameModelImmutable model) {

    }

    @Override
    protected void show_playerJoined(GameModelImmutable gameModel, String nick) {

    }

    @Override
    protected void show_starterCards(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_objectiveCards(GameModelImmutable gameModel) {

    }

    @Override
    protected void show_personalBoard(String nick, GameModelImmutable gameModel) {

    }

    @Override
    protected void show_commonBoard(GameModelImmutable gameModel) {

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
    protected void show_whereToDrawFrom() {

    }

    @Override
    protected void show_youReadyToStart(GameModelImmutable gameModel, String nicknameofyou) {

    }

    @Override
    protected void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname) {

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
    protected void show_cardPlaced(GameModelImmutable model, String nickname) {

    }

    @Override
    protected void show_NaNMsg() {

    }

    @Override
    protected void show_returnToMenuMsg() {

    }

    @Override
    protected void show_askCardCoordinatesMainMsg() {

    }

    @Override
    protected void show_orientation(String message) {

    }

    @Override
    protected void show_askChooseCardMainMsg() {

    }

    @Override
    protected void show_genericMessage(String s) {

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

    @Override
    protected int getLengthLongestMessage(GameModelImmutable model) {
        return 0;
    }

    /**
     * This method send a message
     * @param msg   the message to add
     * @param model the model to which add the message
     */
    @Override
    protected void addMessage(Message msg, GameModelImmutable model) {
        show_sentMessage(model, model.getChat().getLastMessage().getSender().getNickname());
    }

    /**
     * This method show the sent message
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    @Override
    protected void show_sentMessage(GameModelImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showMessages(model, this.nickname));
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
        callPlatformRunLater(() -> this.guiApplication.showError("Connection to server lost!", true));
    }

}
