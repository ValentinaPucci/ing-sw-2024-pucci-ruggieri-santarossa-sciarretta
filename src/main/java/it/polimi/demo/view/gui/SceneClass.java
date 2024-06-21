package it.polimi.demo.view.gui;

import it.polimi.demo.view.gui.controllers.GuiInputReaderController;
import javafx.scene.Scene;

import java.util.concurrent.LinkedBlockingQueue;

public class SceneClass {

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


    public void setInputReaderGUI(LinkedBlockingQueue<String> GuiReader){
        if(sceneController !=null) {
            sceneController.setInputReaderGUI(GuiReader);
        }
    }

    public GuiInputReaderController getSceneController(){
        return sceneController;
    }
}
