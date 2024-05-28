package it.polimi.demo.view.gui;

import it.polimi.demo.view.gui.controllers.GenericController;
import it.polimi.demo.view.gui.scene.SceneType;
import javafx.scene.Scene;

public class SceneInfo {
    private Scene scene;
    private SceneType sceneEnum;
    private GenericController genericController;

    /**
     * Constructor of the class.
     * @param scene the scene {@link Scene}
     * @param sceneEnum the scene enum {@link SceneType}
     * @param gc the generic controller {@link GenericController}
     */
    public SceneInfo(Scene scene, SceneType sceneEnum, GenericController gc) {
        this.scene = scene;
        this.sceneEnum = sceneEnum;
        this.genericController=gc;
    }
}
