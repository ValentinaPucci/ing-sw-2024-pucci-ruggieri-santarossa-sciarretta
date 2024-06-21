package it.polimi.demo.view.gui;

import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.view.dynamic.utilities.TypeConnection;
import it.polimi.demo.view.gui.controllers.GuiInputReaderController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class FXApplication extends Application {

    private Stage mainStage;
    private ArrayList<SceneClass> sceneCollection;
    private double previousWidth, previousHeight;
    private boolean isResized = true;
    private final Scale scaleTransform = new Scale(1, 1);
    private final ChangeListener<Number> resizeListener = this::resizeListener;


    @Override
    public void start(Stage mainStage) {
        new GameDynamic(this, TypeConnection.valueOf(getParameters().getUnnamed().getFirst()));
        this.mainStage = mainStage;
        initializeScenes();
    }

    private void initializeScenes() {
        sceneCollection = new ArrayList<>();
        for (SceneClass.SceneType sceneType : SceneClass.SceneType.values()) {
            String fxmlPath = sceneType.getFxmlPath();
            if (fxmlPath == null || fxmlPath.isEmpty()) {
                System.err.println("FXML path not valid for: " + sceneType.name());
                continue;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent sceneRoot = fxmlLoader.load();
                GuiInputReaderController sceneController = fxmlLoader.getController();
                Scene scene = new Scene(sceneRoot);
                sceneRoot.getTransforms().add(scaleTransform);
                sceneCollection.add(new SceneClass(scene, sceneType, sceneController));
            } catch (IOException e) {
                throw new RuntimeException("Error occurred while loading " + fxmlPath, e);
            }
        }
    }

    public void setCurrentScene(SceneClass.SceneType sceneType) {
        isResized = false;
        int index = getSceneIndex(sceneType);
        if (index != -1) {
            SceneClass sceneInfo = sceneCollection.get(index);
            Scene currentScene = sceneInfo.getCurrentScene();

            currentScene.getRoot().getTransforms().add(scaleTransform);

            this.mainStage.setScene(currentScene);
            this.mainStage.show();

            updatePreviousDimensions();
            removeResizeListeners();
            addResizeListeners();

            isResized = true;
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
            double scaleX = width / previousWidth;
            double scaleY = height / previousHeight;
            previousWidth = width;
            previousHeight = height;
            scaleTransform.setX(scaleX);
            scaleTransform.setY(scaleY);
        }
    }

    private int getSceneIndex(SceneClass.SceneType sceneType) {
        for (int i = 0; i < sceneCollection.size(); i++) {
            if (sceneCollection.get(i).getSceneType() == sceneType) {
                return i;
            }
        }
        return -1;
    }

    public void setGUIReaderToScenes(LinkedBlockingQueue<String> guiReader) {
        for (SceneClass scene : sceneCollection) {
            scene.setInputReaderGUI(guiReader);
        }
    }

    public GuiInputReaderController getSceneController(SceneClass.SceneType sceneType) {
        int index = getSceneIndex(sceneType);
        return index != -1 ? sceneCollection.get(index).getSceneController() : null;
    }

}
