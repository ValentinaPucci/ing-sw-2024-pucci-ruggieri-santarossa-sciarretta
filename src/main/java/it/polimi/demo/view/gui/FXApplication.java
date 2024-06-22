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
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;


public class FXApplication extends Application {

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

        SceneType(String fxmlPath) {
            this.fxmlPath = fxmlPath;
        }

        public String getFxmlPath() {
            return fxmlPath;
        }
    }

    private Stage mainStage;
    private Map<SceneType, Scene> sceneMap;
    private Map<SceneType, GuiInputReaderController> controllerMap;
    private double previousWidth, previousHeight;
    private boolean isResized = true;
    private final Scale scaleTransform = new Scale(1, 1);
    private final ChangeListener<Number> resizeListener = this::resizeListener;

    @Override
    public void start(Stage mainStage) {
        new GameDynamic(this, TypeConnection.valueOf(getParameters().getUnnamed().get(0)));
        this.mainStage = mainStage;
        this.sceneMap = new EnumMap<>(SceneType.class);
        this.controllerMap = new EnumMap<>(SceneType.class);
        initializeScenes();
    }

    private void initializeScenes() {
        for (SceneType type : SceneType.values()) {
            try {
                createAndStoreScene(type);
            } catch (IOException e) {
                System.err.println("Failed to load scene for type: " + type + ", error: " + e.getMessage());
            }
        }
    }

    private void createAndStoreScene(SceneType type) throws IOException {
        String fxmlPath = type.getFxmlPath();
        if (fxmlPath == null || fxmlPath.isEmpty()) {
            System.err.println("Invalid FXML path for: " + type);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        GuiInputReaderController controller = loader.getController();
        Scene scene = new Scene(root);
        root.getTransforms().add(scaleTransform);

        sceneMap.put(type, scene);
        controllerMap.put(type, controller);
    }

    public void changeScene(SceneType sceneType) {
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

    public GuiInputReaderController getSceneController(SceneType type) {
        return controllerMap.get(type);
    }

}