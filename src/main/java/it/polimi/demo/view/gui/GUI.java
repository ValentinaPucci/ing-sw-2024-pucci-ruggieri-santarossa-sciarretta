package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.view.dynamic.UI;

import it.polimi.demo.view.gui.controllers.GameOverController;
import it.polimi.demo.view.gui.controllers.LobbyController;
import it.polimi.demo.view.gui.controllers.RunningController;
import it.polimi.demo.view.gui.scene.SceneType;
import javafx.application.Platform;

import java.util.ArrayList;
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
        initializer();
    }

    @Override
    public void initializer() {
        relevant_facts = new ArrayList<>();
    }

    public void callPlatformRunLater(Runnable r) {
        //Need to use this method to call any methods inside the GuiApplication
        //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
        Platform.runLater(r);
    }

    @Override
    public void show_options() {
        callPlatformRunLater(() -> this.guiApplication.setInputReaderGUItoAllControllers(this.GuiReader));//So the controllers can offer text to the buffer for the gameflow
        callPlatformRunLater(() -> this.guiApplication.createNewWindowWithStyle());
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MENU));
    }



    @Override
    public void show_createGame(String nickname) {
    }

    @Override
    public void show_joinRandom(String nickname) {
    }

    @Override
    public void show_join(int idGame, String nickname) {
        show_insertGameId();
    }

    @Override
    public void show_insertGameId() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ID_GAME));
    }

    @Override
    public void show_insertNickname() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NICKNAME));
    }

    @Override
    public void show_insertNumOfPlayers() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NUM_PLAYERS));
    }

    /**
     * This method show that the game has started
     *
     * @param model model where the game has started
     */
    @Override
    public void show_gameStarted(ModelView model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.RUNNING));
        callPlatformRunLater(() -> this.guiApplication.showRunningModel(model, nickname));
    }

    @Override
    public void show_chosenNickname(String nickname) {
        this.nickname = nickname;
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.LOBBY));
    }

    @Override
    public void show_nextTurn(ModelView model, String nickname) {
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
    public void show_readyToStart(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.disableBtnReadyToStart());
    }

    /**
     * This method show that there are no available games to join
     *
     * @param msgToVisualize message that needs visualisation
     */
    @Override
    public void show_noAvailableGamesToJoin(String msgToVisualize) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
        callPlatformRunLater(() -> this.guiApplication.showError(msgToVisualize));
    }

    @Override
    public void show_gameEnded(ModelView model) {
        callPlatformRunLater(() -> ((GameOverController) this.guiApplication.getController(SceneType.GAME_OVER)).show(model));

        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GAME_OVER));

    }

    @Override
    public void show_playerJoined(ModelView gameModel, String nick) {
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
    public void show_starterCards(ModelView gameModel) {

    }

    @Override
    public void show_objectiveCards(ModelView gameModel) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableObjectiveCardsClick());

    }

    @Override
    public void show_personalBoard(String nick, ModelView gameModel) {

    }

    @Override
    public void show_commonBoard(ModelView gameModel) {
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getController(SceneType.RUNNING)).setCommonCards(gameModel));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getController(SceneType.RUNNING)).setScoreBoardPosition(gameModel));
    }

    @Override
    public void show_myTurnIsFinished() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).myTurnIsFinished());
    }

    @Override
    public void show_playerHand(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).setCardHand(gameModel, nickname));
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableCommonCardsClick());
    }


    @Override
    public void show_cardDrawn(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getController(SceneType.RUNNING)).setCommonCards(gameModel));
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).setCardHand(gameModel, nickname));
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getController(SceneType.RUNNING)).setScoreBoardPosition(gameModel)); //added
    }

    @Override
    public void show_othersPersonalBoard(ModelView modelView, int playerIndex) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).setOthersPersonalBoard(modelView.getAllPlayers().get(playerIndex).getPersonalBoard(), playerIndex));

    }

    @Override
    public void show_personalObjectiveCard(ModelView gameModel) {

    }

    @Override
    public void show_cardChosen(String nickname, ModelView model) {

    }

    @Override
    public void show_illegalMove() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).illegalMove());
    }

    @Override
    public void show_illegalMoveBecauseOf(String message) {

    }

    @Override
    public void show_successfulMove(Coordinate coord) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).successfulMove(coord));

    }

    @Override
    public void show_whereToDrawFrom() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableCommonCardsClick());
    }

    @Override
    public void show_commonObjectives(ModelView gameModel) {

    }



    @Override
    public void show_whichObjectiveToChoose() {

    }

    @Override
    public void show_whichCardToPlace() {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).whichCardToPlace());
    }

    @Override
    public void show_pawnPositions(ModelView model){
        callPlatformRunLater(() -> ((RunningController)this.guiApplication.getController(SceneType.RUNNING)).setScoreBoardPosition(model));
    }


    @Override
    public void show_invalidInput() {

    }

    @Override
    public void show_menu() {

    }

    @Override
    public void show_orientation(String message) {
        if(message.equals("Choose the orientation of the card to place")) {
            //System.out.println("GUI: whichOrientationToPlace()");
            callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).whichOrientationToPlace());
        }
        else if(message.equals("Choose the orientation of the starter card")){
            callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).ableStarterCardClick());
        }

    }

    @Override
    public void show_genericMessage(String s) {
    }

    @Override
    public void show_genericError(String s) {

    }



    /**
     * This method offer an important event to the list of important events, and show it
     * @param input the string of the important event to offer
     */
    @Override
    public void addRelevantGameFact(String input) {
        relevant_facts.add(input);
        callPlatformRunLater(() -> this.guiApplication.showImportantEvents(this.relevant_facts));
    }

    /**
     * This method reset the important events
     */
    @Override
    public void clearRelevantGameFacts() {
        this.relevant_facts = new ArrayList<>();
        this.nickname = null;
        alreadyShowedLobby = false;
    }

    /**
     * This method show a message about a no connection error
     */
    @Override
    public void show_noConnectionError() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ERROR));
        callPlatformRunLater(() -> this.guiApplication.showError("Connection to server lost!"));
    }

    //-----------------------chat-----------------------
    @Override
    public void show_messageSent(ModelView gameModel, String nickname) {
        callPlatformRunLater(() -> ((RunningController) this.guiApplication.getController(SceneType.RUNNING)).updateChat(gameModel, nickname));
    }

}
