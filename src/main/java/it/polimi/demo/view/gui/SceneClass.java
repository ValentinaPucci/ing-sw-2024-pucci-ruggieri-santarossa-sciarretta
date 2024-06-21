package it.polimi.demo.view.gui;

import it.polimi.demo.view.gui.controllers.GuiInputReaderController;
import javafx.scene.Scene;

import java.util.concurrent.LinkedBlockingQueue;

public class SceneClass {

    public enum SceneType {
        MENU("/fxml/Menu.fxml"),
        NICKNAME("/fxml/InsertNickname.fxml"),
        ID_GAME("/fxml/InsertIDgame.fxml"),
        NUM_PLAYERS("/fxml/InsertNumPlayers.fxml"),
        GENERIC_WAITING_ROOM("/fxml/GenericWaitingRoom.fxml"),
        RUNNING("/fxml/Running.fxml"),
        GAME_OVER("/fxml/GameOver.fxml"),
        ERROR("/fxml/Error.fxml");

        private final String fxmlPath;

        SceneType(final String fxmlPath) {
            this.fxmlPath = fxmlPath;
        }

        public String getFxmlPath() {
            return fxmlPath;
        }
    }

    private Scene currentScene;
    private SceneType sceneType;
    private GuiInputReaderController sceneController;

    public SceneClass(Scene currentScene, SceneType sceneType, GuiInputReaderController sceneController) {
        this.currentScene = currentScene;
        this.sceneType = sceneType;
        this.sceneController = sceneController;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public SceneType getSceneType() {
        return sceneType;
    }

    public void setInputReaderGUI(LinkedBlockingQueue<String> guiReader) {
        if (sceneController != null) {
            sceneController.setInputReaderGUI(guiReader);
        }
    }

    public GuiInputReaderController getSceneController() {
        return sceneController;
    }
}