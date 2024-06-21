package it.polimi.demo.view.gui.utils;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.scene.Scene;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * SceneClass class.
 * This class is used to store information about a scene.
 */
public record SceneClass(Scene currentScene, SceneType sceneType, GuiInputReaderController sceneController) {

    /**
     * Constructor of the class.
     * @param currentScene the current scene {@link Scene}
     * @param sceneType the scene type {@link SceneType}
     * @param sceneController the scene controller {@link GuiInputReaderController}
     */
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

    /**
     * setter for the input reader GUI.
     */
    public void setInputReaderGUI(LinkedBlockingQueue<String> guiReader) {
        if (sceneController != null) {
            sceneController.reader = guiReader;
        }
    }
}

