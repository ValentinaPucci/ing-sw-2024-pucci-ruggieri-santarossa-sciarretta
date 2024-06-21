package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.view.dynamic.UI;
import it.polimi.demo.view.gui.controllers.EndGameController;
import it.polimi.demo.view.gui.controllers.RunningController;
import it.polimi.demo.view.gui.controllers.StartGameController;
import javafx.application.Platform;

import java.util.concurrent.LinkedBlockingQueue;

public class GUI extends UI {

    private FXApplication fxApplication;
    private LinkedBlockingQueue<String> GuiReader;
    private boolean gameStarted = false;
    private String nickname;

    public GUI(FXApplication guiApplication, LinkedBlockingQueue<String> GuiReader) {
        this.fxApplication = guiApplication;
        this.GuiReader = GuiReader;
        nickname = null;
    }

    private void setSceneWithController(SceneClass.SceneType sceneType, Runnable controllerSetup) {
        Platform.runLater(() -> {
            controllerSetup.run();
            this.fxApplication.setCurrentScene(sceneType);
        });
    }

    @Override
    protected void startFirstScene() {
        Platform.runLater(() -> {
            this.fxApplication.setGUIReaderToScenes(this.GuiReader);
            this.fxApplication.setCurrentScene(SceneClass.SceneType.MENU);
        });
    }


    @Override
    protected void show_join(int idGame, String nickname) {
        setSceneWithController(SceneClass.SceneType.ID_GAME, () -> {});
    }

    @Override
    protected void show_insertGameId() {
        setSceneWithController(SceneClass.SceneType.ID_GAME, () -> {});
    }

    @Override
    protected void show_insertNickname() {
        setSceneWithController(SceneClass.SceneType.NICKNAME, () -> {});
    }

    @Override
    protected void show_insertNumOfPlayers() {
        setSceneWithController(SceneClass.SceneType.NUM_PLAYERS, () -> {});
    }


    @Override
    protected void show_gameStarted(ModelView model) {
        setSceneWithController(SceneClass.SceneType.RUNNING, () -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.setCardHand(model, nickname);
            runningController.setStarterCardFront(model, nickname);
            runningController.setScoreBoardPosition(model);
            runningController.setPlayersPointsAndNicknames(model, nickname);
            runningController.setCommonCards(model);
            runningController.setPersonalObjectives(model, nickname);
        });
    }

    @Override
    protected void show_chosenNickname(String nickname) {
        this.nickname = nickname;
        setSceneWithController(SceneClass.SceneType.GENERIC_WAITING_ROOM, () -> {});
    }

    @Override
    protected void show_nextTurn(ModelView model, String nickname) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.changeTurn(model, nickname);
        });
    }

    @Override
    protected void show_readyToStart(ModelView gameModel, String nickname) {
        Platform.runLater(() -> {
            StartGameController waitingRoomController = (StartGameController) this.fxApplication.getSceneController(SceneClass.SceneType.GENERIC_WAITING_ROOM);
            waitingRoomController.setReadyButton(false);
        });
    }

    @Override
    protected void show_gameEnded(ModelView model) {
        setSceneWithController(SceneClass.SceneType.GAME_OVER, () -> {
            EndGameController gameOverController = (EndGameController) this.fxApplication.getSceneController(SceneClass.SceneType.GAME_OVER);
            gameOverController.show(model);
        });
    }

    @Override
    protected void show_playerJoined(ModelView gameModel, String nick) {
        if (!gameStarted) {
            this.nickname = nick;
            setSceneWithController(SceneClass.SceneType.GENERIC_WAITING_ROOM, () -> {
                StartGameController waitingRoomController = (StartGameController) this.fxApplication.getSceneController(SceneClass.SceneType.GENERIC_WAITING_ROOM);
                waitingRoomController.setMyNicknameLabel(nick);
                waitingRoomController.setGameId(gameModel.getGameId());
                waitingRoomController.showPlayerToWaitingRoom(gameModel);
            });
            gameStarted = true;
        } else {
            Platform.runLater(() -> {
                StartGameController waitingRoomController = (StartGameController) this.fxApplication.getSceneController(SceneClass.SceneType.GENERIC_WAITING_ROOM);
                waitingRoomController.showPlayerToWaitingRoom(gameModel);
            });
        }
    }


    @Override
    protected void show_objectiveCards(ModelView gameModel) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.ableObjectiveCardsClick();
        });
    }

    @Override
    protected void show_commonBoard(ModelView gameModel) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.setCommonCards(gameModel);
            runningController.setScoreBoardPosition(gameModel);
            runningController.setPoints(gameModel);
        });
    }

    @Override
    protected void show_myTurnIsFinished() {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.myTurnIsFinished();
        });
    }

    @Override
    protected void show_playerHand(ModelView gameModel, String nickname) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.setCardHand(gameModel, nickname);
            runningController.ableCommonCardsClick();
        });
    }

    @Override
    protected void show_cardDrawn(ModelView gameModel, String nickname) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.setCommonCards(gameModel);
            runningController.setCardHand(gameModel, nickname);
            runningController.setScoreBoardPosition(gameModel);
            runningController.setPersonalBoard(gameModel);
            runningController.setPoints(gameModel);
        });
    }

    @Override
    protected void show_othersPersonalBoard(ModelView modelView, int playerIndex) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.setOthersPersonalBoard(playerIndex);
        });
    }

    @Override
    protected void playerLeft(ModelView model, String nick) {
        Platform.runLater(() -> this.fxApplication.setCurrentScene(SceneClass.SceneType.ERROR));
    }

    @Override
    protected void show_cardChosen(String nickname, ModelView model) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.illegalMovePlace();
        });
    }

    @Override
    public void show_illegalMove() {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.illegalMove();
        });
    }

    @Override
    protected void show_illegalMoveBecauseOf(String message) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.illegalMoveBecauseOf(message);
        });
    }

    @Override
    protected void show_successfulMove(Coordinate coord) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.successfulMove(coord);
        });
    }

    @Override
    protected void show_whereToDrawFrom() {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.ableCommonCardsClick();
        });
    }

    @Override
    public void show_whichCardToPlace() {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.whichCardToPlace();
        });
    }

    @Override
    public void show_pawnPositions(ModelView model) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.setScoreBoardPosition(model);
            runningController.setPoints(model);
        });
    }

    @Override
    protected void show_orientation(String message) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            if (message.equals("Choose the orientation of the card to place")) {
                runningController.whichOrientationToPlace();
            } else if (message.equals("Choose the orientation of the starter card")) {
                runningController.ableStarterCardClick();
            }
        });
    }

    @Override
    protected void show_noConnectionError() {
        Platform.runLater(() -> this.fxApplication.setCurrentScene(SceneClass.SceneType.ERROR));
    }

    @Override
    protected void show_messageSent(ModelView gameModel, String nickname) {
        Platform.runLater(() -> {
            RunningController runningController = (RunningController) this.fxApplication.getSceneController(SceneClass.SceneType.RUNNING);
            runningController.updateChat(gameModel, nickname);
        });
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
