package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.view.dynamic.UI;

import it.polimi.demo.view.gui.controllers.GameOverController;
import it.polimi.demo.view.gui.controllers.LobbyController;
import it.polimi.demo.view.gui.controllers.RunningController;
import javafx.application.Platform;

import java.util.concurrent.LinkedBlockingQueue;

public class GUI extends UI {

    private ApplicationGUI guiApplication;
    private LinkedBlockingQueue<String> GuiReader;
    private boolean alreadyShowedLobby = false;
    private String nickname;

    public GUI(ApplicationGUI guiApplication, LinkedBlockingQueue<String> GuiReader) {
        this.guiApplication = guiApplication;
        this.GuiReader = GuiReader;
        nickname = null;
    }

    //Need to use this method to call any methods inside the GuiApplication
    //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
    public void callPlatformRunLater(Runnable r) {
        Platform.runLater(r);
    }

    @Override
    protected void show_options() {
        callPlatformRunLater(() -> this.guiApplication.assignGUIReaderToControllers(this.GuiReader));
        callPlatformRunLater(() -> this.guiApplication.createNewWindowWithStyle());
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MENU));
    }

    @Override
    protected void show_createGame(String nickname) {
    }

    @Override
    protected void show_joinRandom(String nickname) {
    }

    @Override
    protected void show_join(int idGame, String nickname) {
        show_insertGameId();
    }

    @Override
    protected void show_insertGameId() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ID_GAME));
    }

    @Override
    protected void show_insertNickname() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NICKNAME));
    }

    @Override
    protected void show_insertNumOfPlayers() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NUM_PLAYERS));
    }


    @Override
    protected void show_gameStarted(ModelView model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.RUNNING));
        callPlatformRunLater(() -> this.guiApplication.initializeRunning(model, nickname));
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

    @Override
    protected void show_readyToStart(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.disableBtnReadyToStart());
    }

    @Override
    protected void show_gameEnded(ModelView model) {
        callPlatformRunLater(() -> ((GameOverController) this.guiApplication.getSceneController(SceneType.GAME_OVER)).show(model));
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GAME_OVER));
    }

    @Override
    protected void show_playerJoined(ModelView gameModel, String nick) {
        if (!alreadyShowedLobby) {
            this.nickname = nick;
            callPlatformRunLater(() -> ((LobbyController) this.guiApplication.getSceneController(SceneType.LOBBY)).setNicknameLabel(nick));
            callPlatformRunLater(() -> ((LobbyController) this.guiApplication.getSceneController(SceneType.LOBBY)).setGameId(gameModel.getGameId()));

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
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).ableObjectiveCardsClick());

    }

    @Override
    protected void show_personalBoard(String nick, ModelView gameModel) {
    }

    @Override
    protected void show_commonBoard(ModelView gameModel) {
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setCommonCards(gameModel));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setScoreBoardPosition(gameModel));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setPoints(gameModel));
    }

    @Override
    protected void show_myTurnIsFinished() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).myTurnIsFinished());
    }

    @Override
    protected void show_playerHand(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).setCardHand(gameModel, nickname));
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).ableCommonCardsClick());
    }


    @Override
    protected void show_cardDrawn(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setCommonCards(gameModel));
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).setCardHand(gameModel, nickname));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setScoreBoardPosition(gameModel));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setPersonalBoard(gameModel));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setPoints(gameModel));
    }

    @Override
    protected void show_othersPersonalBoard(ModelView modelView, int playerIndex) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).setOthersPersonalBoard(playerIndex));

    }

    @Override
    protected void playerLeft(ModelView model, String nick) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
        System.out.println("Player " + nick + " left the game");
    }

    @Override
    protected void show_personalObjectiveCard(ModelView gameModel) {

    }

    @Override
    protected void show_cardChosen(String nickname, ModelView model) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).illegalMovePlace());
    }

    @Override
    public void show_illegalMove() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).illegalMove());
    }

    @Override
    protected void show_illegalMoveBecauseOf(String message) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).illegalMoveBecauseOf(message));
    }

    @Override
    protected void show_successfulMove(Coordinate coord) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).successfulMove(coord));

    }

    @Override
    protected void show_whereToDrawFrom() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).ableCommonCardsClick());
    }

    @Override
    public void show_whichObjectiveToChoose() {}

    @Override
    public void show_whichCardToPlace() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).whichCardToPlace());
    }

    @Override
    public void show_pawnPositions(ModelView model){
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setScoreBoardPosition(model));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getSceneController(SceneType.RUNNING)).setPoints(model));
    }


    @Override
    protected void show_invalidInput() {

    }

    @Override
    protected void show_menu() {

    }

    @Override
    protected void show_orientation(String message) {
        if(message.equals("Choose the orientation of the card to place")) {
            //System.out.println("GUI: whichOrientationToPlace()");
            callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).whichOrientationToPlace());
        }
        else if(message.equals("Choose the orientation of the starter card")){
            callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).ableStarterCardClick());
        }

    }

    @Override
    protected void show_genericMessage(String s) {
    }

    @Override
    protected void show_genericError(String s) {

    }

    @Override
    protected void show_noConnectionError() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
    }

    //-----------------------chat-----------------------
    @Override
    protected void show_messageSent(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).updateChat(gameModel, nickname));
    }

}
