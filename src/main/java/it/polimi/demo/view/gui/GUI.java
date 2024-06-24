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

/**
 * GUI class is the class that manages the GUI of the application.
 */
public class GUI extends UI {

    private boolean gameStarted = false;
    private String myNickname;
    private FXApplication app;
    private LinkedBlockingQueue<String> GuiReader;

    /** Constructor for the GUI class.
     * @param guiApp the FXApplication object
     * @param GuiReader the LinkedBlockingQueue object that allows to read the input from the GUI
     * */
    public GUI(FXApplication guiApp, LinkedBlockingQueue<String> GuiReader) {
        this.app = guiApp;
        this.GuiReader = GuiReader;
        myNickname = null;
    }

    /** Method that executes the given action on the JavaFX application thread.
     * */
    private void executeOnPlatform(Runnable action) {
        Platform.runLater(action);
    }

    /** Method that execute actions on the RunningController.
     * */
    private void modifyRunningController(Consumer<RunningController> action) {
        executeOnPlatform(() -> action.accept((RunningController) app.getSceneController("Running")));
    }

    /** Method that switches the scene to the given sceneType.
     * */
    private void switchScene(String sceneType) {
        executeOnPlatform(() -> app.changeScene(sceneType));
    }

    //------------------------------------methods used in both TUI and GUI----------------------------------------------

    /** Method that starts the first scene of the application.
     * It shows the Menu scene.
     * */
    @Override
    protected void startFirstScene() {
        executeOnPlatform(() -> {
            this.app.setGUIReaderToScenes(this.GuiReader);
            this.app.changeScene("Menu");
        });
    }

    /** Method that shows the InsertIDgame scene.
     * */
    @Override
    protected void show_insertGameId() {
        switchScene("InsertIDgame");
    }

    /** Method that shows the InsertNickname scene.
     * */
    @Override
    protected void show_insertNickname() {
        switchScene("InsertNickname");
    }

    /** Method that shows the InsertNumPlayers scene.
     * */
    @Override
    protected void show_insertNumOfPlayers() {
        switchScene("InsertNumPlayers");
    }

    /** Method that shows the GenericWaitingRoom scene.
     * */
    @Override
    protected void show_chosenNickname(String nickname) {
        this.myNickname = nickname;
        switchScene("GenericWaitingRoom");
    }

    /** Method that shows the GameOver scene.
     * */
    @Override
    protected void show_gameEnded(ModelView model) {
        executeOnPlatform(() -> {
            ((EndGameController) this.app.getSceneController("GameOver")).show(model);
            switchScene("GameOver");
        });
    }

    /** Method that shows the Running scene and initializes all the elements of the scene.
     * */
    @Override
    protected void show_gameStarted(ModelView model) {
        modifyRunningController(controller -> {
            switchScene("Running");
            controller.setCardHand(model, myNickname);
            controller.setStarterCardFront(model, myNickname);
            controller.setScoreBoardPosition(model);
            controller.setGridPaneAndChat(model, myNickname);
            controller.setCommonCards(model);
            controller.setPersonalObjectives(model, myNickname);
        });
    }

    /** Method that shows who is the first player to play
     * */
    @Override
    protected void show_nextTurn(ModelView model, String nickname) {
        modifyRunningController(controller -> controller.changeTurn(model));
    }

    /** Method that hides the ready button.
     * */
    @Override
    protected void show_readyToStart(ModelView gameModel, String nickname) {
        executeOnPlatform(() -> ((StartGameController) this.app.getSceneController("GenericWaitingRoom")).ReadyButtonVisibility(false));
    }

    /** Method that shows the player that joined the game.
     * It updated the GenericWaitingRoom scene.
     * */
    @Override
    protected void show_playerJoined(ModelView gameModel, String nick) {
        if (!gameStarted) {
            executeOnPlatform(() -> {
                StartGameController waitingRoomController = (StartGameController) app.getSceneController("GenericWaitingRoom");
                waitingRoomController.setMyNickname(nick);
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


    /** Method that ables the click on the personal objective cards
     * */
    @Override
    protected void show_objectiveCards(ModelView gameModel) {
        modifyRunningController(RunningController::ableObjectiveCardsClick);
    }

    /** Method that updates the common cards, score board position and points.
     * */
    @Override
    protected void show_commonBoard(ModelView gameModel) {
        modifyRunningController(controller -> {
            controller.setCommonCards(gameModel);
            controller.setScoreBoardPosition(gameModel);
            controller.setPoints(gameModel);
        });
    }

    /** Method that shows the end of my turn
     * */
    @Override
    protected void show_myTurnIsFinished() {
        modifyRunningController(RunningController::myTurnIsFinished);
    }

    /** Method that shows my hand of cards
     * */
    @Override
    protected void show_playerHand(ModelView gameModel, String nickname) {
        modifyRunningController(controller -> {
            controller.setCardHand(gameModel, nickname);
            controller.ableCommonCardsClick();
        });
    }

    /** Method that updates the running scene for all the players.
     * */
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

    /** Method that shows the personal board of the given player.
     * */
    @Override
    protected void show_othersPersonalBoard(ModelView modelView, int playerIndex) {
        modifyRunningController(controller -> controller.setOthersPersonalBoard(playerIndex));
    }

    /** Method that shows the GameOver scene when a player decides to leave the game.
     * */
    @Override
    protected void playerLeft(ModelView model, String nick) {
        switchScene("GameOver");
    }

    /** Method that disables all other cards except the one chosen.
     * */
    @Override
    protected void show_cardChosen(String nickname, ModelView model) {
        modifyRunningController(RunningController::disableAllOtherCardsInHand);
    }

    /** Method that shows the illegal move attempt
     * */
    @Override
    public void show_illegalMove() {
        modifyRunningController(RunningController::illegalMove);
    }

    /** Method that shows the successful move and then places the card in the personal board.
     * */
    @Override
    protected void show_successfulMove(Coordinate coord) {
        modifyRunningController(controller -> controller.successfulMove(coord));
    }

    /** Method that allows the click on the common cards
     * */
    @Override
    protected void show_whereToDrawFrom() {
        modifyRunningController(RunningController::ableCommonCardsClick);
    }

    /** Method that allows the click on the player hand
     * */
    @Override
    public void show_whichCardToPlace() {
        modifyRunningController(RunningController::whichCardToPlace);
    }

    /** Method that updates the position on the score board
     * */
    @Override
    public void show_pawnPositions(ModelView model) {
        modifyRunningController(controller -> {
            controller.setScoreBoardPosition(model);
            controller.setPoints(model);
        });
    }

    /** Method that allows to show the orientation on the chosen card
     * */
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

    /** Method shows the error scene, due to connection lost.
     * */
    @Override
    protected void show_noConnectionError() {
        switchScene("Error");
    }

    /** Method that shows the message sent in the chat.
     * */
    @Override
    protected void show_messageSent(ModelView gameModel, String nickname) {
        modifyRunningController(controller -> controller.updateChat(gameModel, nickname));
    }

    //------------------------------------methods used only in TUI----------------------------------------------

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

    @Override
    protected void show_illegalMoveBecauseOf(String message) {
    }

    @Override
    protected void show_join(int idGame, String nickname) {
    }
}

