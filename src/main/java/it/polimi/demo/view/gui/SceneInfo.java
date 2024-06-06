package it.polimi.demo.view.gui;

import it.polimi.demo.view.flow.utilities.GuiReader;
import it.polimi.demo.view.gui.controllers.GenericController;
import it.polimi.demo.view.gui.scene.SceneType;
import javafx.scene.Scene;

public class SceneInfo {
    /**
     * Attributes.
     */
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

    /**
     * Method to get the scene.
     * @return the scene {@link Scene}
     */
    public Scene getScene() {
        return scene;
    }


    /**
     * Method to get the scene enum.
     * @return the scene enum {@link SceneType}
     */
    public SceneType getSceneType() {
        return sceneEnum;
    }

    /**
     * Method to set the input reader GUI.
     * @param GuiReader the input reader GUI {@link GuiReader}
     */
    public void setInputReaderGUI(GuiReader GuiReader){
        //System.out.println("setInputReaderGUI: "+ inputReaderGUI);
        if(genericController!=null) {
            genericController.setInputReaderGUI(GuiReader);
        }
    }

    /**
     * Method to get the generic controller.
     * @return the generic controller {@link GenericController}
     */
    public GenericController getGenericController(){
        return genericController;
    }
}
