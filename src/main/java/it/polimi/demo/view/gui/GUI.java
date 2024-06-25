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
    protected void startTheGame() {
        executeOnPlatform(() -> {
            this.app.setGUIReaderToScenes(this.GuiReader);
            this.app.changeScene("Menu");
        });
    }

    /** Method that shows the InsertIDgame scene.
     * */
    @Override
    protected void displayInsertGameId() {
        switchScene("InsertIDgame");
    }

    /** Method that shows the InsertNickname scene.
     * */
    @Override
    protected void displayInsertNickname() {
        switchScene("InsertNickname");
    }

    /** Method that shows the InsertNumPlayers scene.
     * */
    @Override
    protected void displayInsertNumOfPlayers() {
        switchScene("InsertNumPlayers");
    }

    /** Method that shows the GenericWaitingRoom scene.
     * */
    @Override
    protected void displayChosenNickname(String nickname) {
        this.myNickname = nickname;
        switchScene("GenericWaitingRoom");
    }

    /** Method that shows the GameOver scene.
     * */
    @Override
    protected void displayGameEnded(ModelView model) {
        executeOnPlatform(() -> {
            ((EndGameController) this.app.getSceneController("GameOver")).show(model);
            switchScene("GameOver");
        });
    }

    /** Method that shows the Running scene and initializes all the elements of the scene.
     * */
    @Override
    protected void displayGameStarted(ModelView model) {
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
    protected void displayNextTurn(ModelView model, String nickname) {
        modifyRunningController(controller -> controller.changeTurn(model));
    }

    /** Method that hides the ready button.
     * */
    @Override
    protected void displayReadyToStart(ModelView gameModel, String nickname) {
        executeOnPlatform(() -> ((StartGameController) this.app.getSceneController("GenericWaitingRoom")).ReadyButtonVisibility(false));
    }

    /** Method that shows the player that joined the game.
     * It updated the GenericWaitingRoom scene.
     * */
    @Override
    protected void displayPlayerJoined(ModelView gameModel, String nick) {
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
    protected void displayObjectiveCards(ModelView gameModel) {
        modifyRunningController(RunningController::ableObjectiveCardsClick);
    }

    /** Method that updates the common cards, score board position and points.
     * */
    @Override
    protected void displayCommonBoard(ModelView gameModel) {
        modifyRunningController(controller -> {
            controller.setCommonCards(gameModel);
            controller.setScoreBoardPosition(gameModel);
            controller.setPoints(gameModel);
        });
    }

    /** Method that shows the end of my turn
     * */
    @Override
    protected void displayMyTurnIsFinished() {
        modifyRunningController(RunningController::myTurnIsFinished);
    }

    /** Method that shows my hand of cards
     * */
    @Override
    protected void displayPlayerHand(ModelView gameModel, String nickname) {
        modifyRunningController(controller -> {
            controller.setCardHand(gameModel, nickname);
            controller.ableCommonCardsClick();
        });
    }

    /** Method that updates the running scene for all the players.
     * */
    @Override
    protected void displayCardDrawn(ModelView gameModel, String nickname) {
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
    protected void displayOthersPersonalBoard(ModelView modelView, int playerIndex) {
        modifyRunningController(controller -> controller.setOthersPersonalBoard(playerIndex));
    }

    /** Method that shows the GameOver scene when a player decides to leave the game.
     * */
    @Override
    protected void displayPlayerLeft(ModelView model, String nick) {
        switchScene("GameOver");
    }

    /** Method that disables all other cards except the one chosen.
     * */
    @Override
    protected void displayCardChosen(String nickname, ModelView model) {
        modifyRunningController(RunningController::disableAllOtherCardsInHand);
    }

    /** Method that shows the illegal move attempt
     * */
    @Override
    public void displayIllegalMove() {
        modifyRunningController(RunningController::illegalMove);
    }

    /** Method that shows the successful move and then places the card in the personal board.
     * */
    @Override
    protected void displaySuccessfulMove(Coordinate coord) {
        modifyRunningController(controller -> controller.successfulMove(coord));
    }

    /** Method that allows the click on the common cards
     * */
    @Override
    protected void displayWhereToDrawFrom() {
        modifyRunningController(RunningController::ableCommonCardsClick);
    }

    /** Method that allows the click on the player hand
     * */
    @Override
    public void displayWhichCardToPlace() {
        modifyRunningController(RunningController::whichCardToPlace);
    }

    /** Method that updates the position on the score board
     * */
    @Override
    public void displayPawnPositions(ModelView model) {
        modifyRunningController(controller -> {
            controller.setScoreBoardPosition(model);
            controller.setPoints(model);
        });
    }

    /** Method that allows to show the orientation on the chosen card
     * */
    @Override
    protected void displayOrientation(String message) {
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
    protected void displayNoConnectionError() {
        switchScene("Error");
    }

    /** Method that shows the message sent in the chat.
     * */
    @Override
    protected void displayMessageSent(ModelView gameModel, String nickname) {
        modifyRunningController(controller -> controller.updateChat(gameModel, nickname));
    }

    //------------------------------------methods used only in TUI----------------------------------------------

    @Override
    protected void displayGenericMessage(String s) {
    }

    @Override
    protected void displayGenericError(String s) {

    }

    @Override
    protected void displayInvalidInput() {
    }

    @Override
    protected void displayMenu() {
    }

    @Override
    protected void displayPersonalObjectiveCard(ModelView gameModel) {

    }
    @Override
    public void displayWhichObjectiveToChoose() {}

    @Override
    protected void displayStarterCards(ModelView gameModel) {

    }

    @Override
    protected void displayPersonalBoard(String nick, ModelView gameModel) {
    }

    @Override
    protected void displayCreateGame(String nickname) {
    }

    @Override
    protected void displayJoinRandom(String nickname) {
    }

    @Override
    protected void displayIllegalMoveBecauseOf(String message) {
    }

    @Override
    protected void displayJoin(int idGame, String nickname) {
    }
}

