package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.view.dynamic.UI;

import it.polimi.demo.view.gui.controllers.GameOverController;
import it.polimi.demo.view.gui.controllers.LobbyController;
import it.polimi.demo.view.gui.controllers.RunningController;
import it.polimi.demo.view.gui.utils.SceneType;
import javafx.application.Platform;

import java.util.concurrent.LinkedBlockingQueue;

public class GUI extends UI {

    private FXApplication guiApplication;
    private LinkedBlockingQueue<String> GuiReader;
    private boolean alreadyShowedLobby = false;
    private String nickname;

    public GUI(FXApplication guiApplication, LinkedBlockingQueue<String> GuiReader) {
        this.guiApplication = guiApplication;
        this.GuiReader = GuiReader;
        nickname = null;
    }

    //Need to use this method to call any methods inside the GuiApplication
    //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
    public void runOnThread(Runnable r) {
        Platform.runLater(r);
    }

    @Override
    protected void show_options() {
        runOnThread(() -> this.guiApplication.setGUIReaderToScenes(this.GuiReader));
        runOnThread(guiApplication::newWindow);
        runOnThread(() -> this.guiApplication.setActiveScene(SceneType.MENU));
    }


    @Override
    protected void show_join(int idGame, String nickname) {
        show_insertGameId();
    }

    @Override
    protected void show_insertGameId() {
        runOnThread(() -> this.guiApplication.setActiveScene(SceneType.ID_GAME));
    }

    @Override
    protected void show_insertNickname() {
        runOnThread(() -> this.guiApplication.setActiveScene(SceneType.NICKNAME));
    }

    @Override
    protected void show_insertNumOfPlayers() {
        runOnThread(() -> this.guiApplication.setActiveScene(SceneType.NUM_PLAYERS));
    }


    @Override
    protected void show_gameStarted(ModelView model) {
        runOnThread(() -> {
            RunningController runningController = (RunningController) this.guiApplication.getSceneController(SceneType.RUNNING);
            this.guiApplication.setActiveScene(SceneType.RUNNING);

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
        runOnThread(() -> this.guiApplication.setActiveScene(SceneType.LOBBY));
    }

    @Override
    protected void show_nextTurn(ModelView model, String nickname) {
        if (!alreadyShowedLobby) {
            show_gameStarted(model);
            alreadyShowedLobby = true;
        }
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).changeTurn(model, nickname));
    }

    @Override
    protected void show_readyToStart(ModelView gameModel, String nickname) {
        runOnThread(() -> ((LobbyController)this.guiApplication.getSceneController(SceneType.LOBBY)).setVisibleBtnReady(false));
    }

    protected void show_gameEnded(ModelView model) {
        runOnThread(() -> {
            GameOverController gameOverController = (GameOverController) this.guiApplication.getSceneController(SceneType.GAME_OVER);

            gameOverController.show(model);
            this.guiApplication.setActiveScene(SceneType.GAME_OVER);
        });
    }

    @Override
    protected void show_playerJoined(ModelView gameModel, String nick) {
        if (!alreadyShowedLobby) {
            this.nickname = nick;
            runOnThread(() -> {
                LobbyController lobbyController = (LobbyController) this.guiApplication.getSceneController(SceneType.LOBBY);

                lobbyController.setNicknameLabel(nick);
                lobbyController.setGameId(gameModel.getGameId());
                this.guiApplication.setActiveScene(SceneType.LOBBY);
                this.guiApplication.showPlayerToLobby(gameModel);
            });
            alreadyShowedLobby = true;
        } else {
            runOnThread(() -> this.guiApplication.showPlayerToLobby(gameModel));
        }
    }


    @Override
    protected void show_objectiveCards(ModelView gameModel) {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).ableObjectiveCardsClick());

    }

    @Override
    protected void show_commonBoard(ModelView gameModel) {
        runOnThread(() -> {
            RunningController runningController = (RunningController) this.guiApplication.getSceneController(SceneType.RUNNING);

            runningController.setCommonCards(gameModel);
            runningController.setScoreBoardPosition(gameModel);
            runningController.setPoints(gameModel);
        });
    }

    @Override
    protected void show_myTurnIsFinished() {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).myTurnIsFinished());
    }

    @Override
    protected void show_playerHand(ModelView gameModel, String nickname) {
        runOnThread(() -> {
            RunningController runningController = (RunningController) this.guiApplication.getSceneController(SceneType.RUNNING);

            runningController.setCardHand(gameModel, nickname);
            runningController.ableCommonCardsClick();
        });
    }


    @Override
    protected void show_cardDrawn(ModelView gameModel, String nickname) {
        runOnThread(() -> {
            RunningController runningController = (RunningController) this.guiApplication.getSceneController(SceneType.RUNNING);

            runningController.setCommonCards(gameModel);
            runningController.setCardHand(gameModel, nickname);
            runningController.setScoreBoardPosition(gameModel);
            runningController.setPersonalBoard(gameModel);
            runningController.setPoints(gameModel);
        });
    }

    @Override
    protected void show_othersPersonalBoard(ModelView modelView, int playerIndex) {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).setOthersPersonalBoard(playerIndex));

    }

    @Override
    protected void playerLeft(ModelView model, String nick) {
        runOnThread(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
    }


    @Override
    protected void show_cardChosen(String nickname, ModelView model) {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).illegalMovePlace());
    }

    @Override
    public void show_illegalMove() {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).illegalMove());
    }

    @Override
    protected void show_illegalMoveBecauseOf(String message) {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).illegalMoveBecauseOf(message));
    }

    @Override
    protected void show_successfulMove(Coordinate coord) {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).successfulMove(coord));

    }

    @Override
    protected void show_whereToDrawFrom() {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).ableCommonCardsClick());
    }

    @Override
    public void show_whichCardToPlace() {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).whichCardToPlace());
    }

    @Override
    public void show_pawnPositions(ModelView model) {
        runOnThread(() -> {
            RunningController runningController = (RunningController) this.guiApplication.getSceneController(SceneType.RUNNING);

            runningController.setScoreBoardPosition(model);
            runningController.setPoints(model);
        });
    }

    @Override
    protected void show_orientation(String message) {
        runOnThread(() -> {
            RunningController runningController = (RunningController) this.guiApplication.getSceneController(SceneType.RUNNING);

            if (message.equals("Choose the orientation of the card to place")) {
                runningController.whichOrientationToPlace();
            } else if (message.equals("Choose the orientation of the starter card")) {
                runningController.ableStarterCardClick();
            }
        });
    }

    @Override
    protected void show_noConnectionError() {
        runOnThread(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
    }

    @Override
    protected void show_messageSent(ModelView gameModel, String nickname) {
        runOnThread(() -> ((RunningController) this.guiApplication.getSceneController(SceneType.RUNNING)).updateChat(gameModel, nickname));
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
