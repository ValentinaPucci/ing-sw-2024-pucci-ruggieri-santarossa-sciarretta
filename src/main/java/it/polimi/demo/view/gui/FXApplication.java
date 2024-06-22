package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.dynamic.utilities.TypeConnection;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.view.gui.controllers.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;


public class FXApplication extends Application {

    private Stage mainStage;
    private Map<String, Scene> sceneMap;
    private Map<String, GuiInputReaderController> controllerMap;
    private double previousWidth, previousHeight;
    private boolean isResized = true;
    private final Scale scaleTransform = new Scale(1, 1);
    private final ChangeListener<Number> resizeListener = this::resizeListener;

    @Override
    public void start(Stage mainStage) {
        new GameDynamic(this, TypeConnection.valueOf(getParameters().getUnnamed().get(0)));
        this.mainStage = mainStage;
        this.sceneMap = new HashMap<>();
        this.controllerMap = new HashMap<>();
        initializeScenes();
    }

    private void initializeScenes() {
        String[] sceneTypes = {
                "/fxml/Menu.fxml", "/fxml/InsertNickname.fxml", "/fxml/InsertIDgame.fxml",
                "/fxml/InsertNumPlayers.fxml", "/fxml/GenericWaitingRoom.fxml", "/fxml/Running.fxml",
                "/fxml/GameOver.fxml", "/fxml/Error.fxml"
        };

        for (String type : sceneTypes) {
            try {
                createAndStoreScene(type);
            } catch (IOException e) {
                System.err.println("Failed to load scene for type: " + type + ", error: " + e.getMessage());
            }
        }
    }

    private void createAndStoreScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        GuiInputReaderController controller = loader.getController();
        Scene scene = new Scene(root);
        root.getTransforms().add(scaleTransform);
        String sceneType = extractSceneTypeFromPath(fxmlPath);
        sceneMap.put(sceneType, scene);
        controllerMap.put(sceneType, controller);
    }

    private String extractSceneTypeFromPath(String fxmlPath) {
        String[] parts = fxmlPath.split("/");
        String fileName = parts[parts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public void changeScene(String sceneType) {
        isResized = false;
        Scene newScene = sceneMap.get(sceneType);
        if (newScene != null) {
            newScene.getRoot().getTransforms().add(scaleTransform);

            mainStage.setScene(newScene);
            mainStage.show();

            updatePreviousDimensions();
            removeResizeListeners();
            addResizeListeners();

            isResized = true;
        } else {
            System.err.println("Scene not found for type: " + sceneType);
        }
    }

    private void updatePreviousDimensions() {
        previousWidth = mainStage.getScene().getWidth();
        previousHeight = mainStage.getScene().getHeight();
    }

    private void removeResizeListeners() {
        mainStage.widthProperty().removeListener(resizeListener);
        mainStage.heightProperty().removeListener(resizeListener);
    }

    private void addResizeListeners() {
        mainStage.widthProperty().addListener(resizeListener);
        mainStage.heightProperty().addListener(resizeListener);
    }

    private void resizeListener(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        resize(mainStage.getWidth(), mainStage.getHeight());
    }

    public void resize(double width, double height) {
        if (isResized) {
            previousWidth = width;
            previousHeight = height;
            scaleTransform.setX(width / previousWidth);
            scaleTransform.setY(height / previousHeight);
        }
    }

    public void setGUIReaderToScenes(LinkedBlockingQueue<String> guiReader) {
        for (GuiInputReaderController controller : controllerMap.values()) {
            if (controller != null) {
                controller.setInputReaderGUI(guiReader);
            }
        }
    }

    public GuiInputReaderController getSceneController(String type) {
        return controllerMap.get(type);
    }

}