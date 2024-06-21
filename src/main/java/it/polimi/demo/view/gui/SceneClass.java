package it.polimi.demo.view.gui;

import it.polimi.demo.view.gui.controllers.GuiInputReaderController;
import javafx.scene.Scene;

import java.util.concurrent.LinkedBlockingQueue;

public record SceneClass(Scene currentScene, SceneType sceneType, GuiInputReaderController sceneController) {

    public SceneClass {
        // Null checks
        if (currentScene == null) {
            throw new IllegalArgumentException("currentScene cannot be null");
        }
        if (sceneType == null) {
            throw new IllegalArgumentException("sceneType cannot be null");
        }
        if (sceneController == null) {
            throw new IllegalArgumentException("sceneController cannot be null");
        }
    }

    public void setInputReaderGUI(LinkedBlockingQueue<String> guiReader) {
        if (sceneController != null) {
            sceneController.reader = guiReader;
        }
    }
}

