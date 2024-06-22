package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.view.dynamic.UI;

import it.polimi.demo.view.gui.controllers.EndGameController;
import it.polimi.demo.view.gui.controllers.RunningController;
import it.polimi.demo.view.gui.controllers.StartGameController;
import javafx.application.Platform;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class GUI extends UI {

    private boolean gameStarted = false;
    private String myNickname;
    private FXApplication app;
    private LinkedBlockingQueue<String> GuiReader;

    public GUI(FXApplication guiApp, LinkedBlockingQueue<String> GuiReader) {
        this.app = guiApp;
        this.GuiReader = GuiReader;
        myNickname = null;
    }

    private void executeOnPlatform(Runnable action) {
        Platform.runLater(action);
    }

    private void modifyRunningController(Consumer<RunningController> action) {
        executeOnPlatform(() -> action.accept((RunningController) app.getSceneController("Running")));
    }

    private void switchScene(String sceneType) {
        executeOnPlatform(() -> app.changeScene(sceneType));
    }

    @Override
    protected void startFirstScene() {
        executeOnPlatform(() -> {
            this.app.setGUIReaderToScenes(this.GuiReader);
            this.app.changeScene("Menu");
        });
    }

    @Override
    protected void show_join(int idGame, String nickname) {
        switchScene("InsertIDgame");
    }

    @Override
    protected void show_insertGameId() {
        switchScene("InsertIDgame");
    }

    @Override
    protected void show_insertNickname() {
        switchScene("InsertNickname");
    }

    @Override
    protected void show_insertNumOfPlayers() {
        switchScene("InsertNumPlayers");
    }

    @Override
    protected void show_gameStarted(ModelView model) {
        modifyRunningController(controller -> {
            switchScene("Running");
            controller.setCardHand(model, myNickname);
            controller.setStarterCardFront(model, myNickname);
            controller.setScoreBoardPosition(model);
            controller.setPlayersPointsAndNicknames(model, myNickname);
            controller.setCommonCards(model);
            controller.setPersonalObjectives(model, myNickname);
        });
    }

    @Override
    protected void show_chosenNickname(String nickname) {
        this.myNickname = nickname;
        switchScene("GenericWaitingRoom");
    }

    @Override
    protected void show_nextTurn(ModelView model, String nickname) {
        modifyRunningController(controller -> controller.changeTurn(model, nickname));
    }

    @Override
    protected void show_readyToStart(ModelView gameModel, String nickname) {
        executeOnPlatform(() -> ((StartGameController) this.app.getSceneController("GenericWaitingRoom")).setReadyButton(false));
    }

    @Override
    protected void show_gameEnded(ModelView model) {
        System.out.println("Sono in Game ended");
        executeOnPlatform(() -> {
            ((EndGameController) this.app.getSceneController("GameOver")).show(model);
            switchScene("GameOver");
        });
    }

    @Override
    protected void show_playerJoined(ModelView gameModel, String nick) {
        if (!gameStarted) {
            executeOnPlatform(() -> {
                StartGameController waitingRoomController = (StartGameController) app.getSceneController("GenericWaitingRoom");
                waitingRoomController.setMyNicknameLabel(nick);
                waitingRoomController.setGameId(gameModel.getGameId());
                waitingRoomController.showPlayerToWaitingRoom(gameModel);
            });
            gameStarted = true;
        } else {
            executeOnPlatform(() -> {
                StartGameController waitingRoomController = (StartGameController) this.app.getSceneController("GenericWaitingRoom");
                waitingRoomController.showPlayerToWaitingRoom(gameModel);
            });
        }
    }


    @Override
    protected void show_objectiveCards(ModelView gameModel) {
        modifyRunningController(RunningController::ableObjectiveCardsClick);
    }

    @Override
    protected void show_commonBoard(ModelView gameModel) {
        modifyRunningController(controller -> {
            controller.setCommonCards(gameModel);
            controller.setScoreBoardPosition(gameModel);
            controller.setPoints(gameModel);
        });
    }

    @Override
    protected void show_myTurnIsFinished() {
        modifyRunningController(RunningController::myTurnIsFinished);
    }

    @Override
    protected void show_playerHand(ModelView gameModel, String nickname) {
        modifyRunningController(controller -> {
            controller.setCardHand(gameModel, nickname);
            controller.ableCommonCardsClick();
        });
    }

    @Override
    protected void show_cardDrawn(ModelView gameModel, String nickname) {
        modifyRunningController(controller -> {
            controller.setCommonCards(gameModel);
            controller.setCardHand(gameModel, nickname);
            controller.setScoreBoardPosition(gameModel);
            controller.setPersonalBoard(gameModel);
            controller.setPoints(gameModel);
        });
    }

    @Override
    protected void show_othersPersonalBoard(ModelView modelView, int playerIndex) {
        modifyRunningController(controller -> controller.setOthersPersonalBoard(playerIndex));
    }

    @Override
    protected void playerLeft(ModelView model, String nick) {
        switchScene("Error");
    }

    @Override
    protected void show_cardChosen(String nickname, ModelView model) {
        modifyRunningController(RunningController::illegalMovePlace);
    }

    @Override
    public void show_illegalMove() {
        modifyRunningController(RunningController::illegalMove);
    }

    @Override
    protected void show_illegalMoveBecauseOf(String message) {
        modifyRunningController(controller -> controller.illegalMoveBecauseOf(message));
    }

    @Override
    protected void show_successfulMove(Coordinate coord) {
        modifyRunningController(controller -> controller.successfulMove(coord));
    }

    @Override
    protected void show_whereToDrawFrom() {
        modifyRunningController(RunningController::ableCommonCardsClick);
    }

    @Override
    public void show_whichCardToPlace() {
        modifyRunningController(RunningController::whichCardToPlace);
    }

    @Override
    public void show_pawnPositions(ModelView model) {
        modifyRunningController(controller -> {
            controller.setScoreBoardPosition(model);
            controller.setPoints(model);
        });
    }

    @Override
    protected void show_orientation(String message) {
        modifyRunningController(controller -> {
            if ("Choose the orientation of the card to place".equals(message)) {
                controller.whichOrientationToPlace();
            } else if ("Choose the orientation of the starter card".equals(message)) {
                controller.ableStarterCardClick();
            }
        });
    }

    @Override
    protected void show_noConnectionError() {
        switchScene("Error");
    }

    @Override
    protected void show_messageSent(ModelView gameModel, String nickname) {
        modifyRunningController(controller -> controller.updateChat(gameModel, nickname));
    }

    //------------------------------------used only in TUI----------------------------------------------

    @Override
    protected void show_genericMessage(String s) {
    }

    @Override
    protected void show_genericError(String s) {

    }

    @Override
    protected void show_invalidInput() {
    }

    @Override
    protected void show_menu() {
    }

    @Override
    protected void show_personalObjectiveCard(ModelView gameModel) {

    }
    @Override
    public void show_whichObjectiveToChoose() {}

    @Override
    protected void show_starterCards(ModelView gameModel) {

    }

    @Override
    protected void show_personalBoard(String nick, ModelView gameModel) {
    }

    @Override
    protected void show_createGame(String nickname) {
    }

    @Override
    protected void show_joinRandom(String nickname) {
    }
}

