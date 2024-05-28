package it.polimi.demo.view.gui;

import it.polimi.demo.view.flow.GameFlow;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ApplicationGUI extends Application {

    private GameFlow gameFlow;

    private Stage primaryStage, popUpStage;
    private StackPane root;
    private ArrayList<SceneInfo> scenes;
    @Override
    public void start(Stage stage) throws Exception {

    }
}
