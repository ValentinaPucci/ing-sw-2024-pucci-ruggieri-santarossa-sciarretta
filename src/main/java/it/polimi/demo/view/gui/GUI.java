package it.polimi.demo.view.gui;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.view.flow.UI;
import it.polimi.demo.view.flow.utilities.inputReaderGUI;

import java.io.IOException;

public class GUI extends UI {

    private ApplicationGUI guiApplication;
    private inputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher = false;
    private boolean alreadyShowedLobby = false;

    private String nickname;

    @Override
    public void init() {

    }

    public GUI(ApplicationGUI guiApplication, inputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI = inputReaderGUI;
        nickname = null;
        init();
    }

    @Override
    protected void show_publisher() throws IOException, InterruptedException {

    }

    @Override
    protected void show_menuOptions() {

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

    @Override
    public void addImportantEvent(String input) {

    }

    @Override
    protected int getLengthLongestMessage(GameModelImmutable model) {
        return 0;
    }

    @Override
    protected void addMessage(Message msg, GameModelImmutable model) {

    }

    @Override
    protected void resetImportantEvents() {

    }

    @Override
    protected void show_noConnectionError() {

    }

}
