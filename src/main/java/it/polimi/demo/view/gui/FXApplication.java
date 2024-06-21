package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.view.dynamic.utilities.TypeConnection;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.view.gui.controllers.*;

import javafx.application.Application;
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
import java.util.concurrent.LinkedBlockingQueue;


public class FXApplication extends Application {

    private Stage mainStage;
    private ArrayList<SceneClass> sceneCollection;
    private double previousWidth, previousHeight;
    private boolean isResized = true;

    @Override
    public void start(Stage mainStage) {
        new GameDynamic(this, TypeConnection.valueOf(getParameters().getUnnamed().getFirst()));
        this.mainStage = mainStage;
        initializeScenes();
    }

    private void initializeScenes() {
        sceneCollection = new ArrayList<>();
        for (SceneType sceneType : SceneType.values()) {
            String fxmlPath = sceneType.getFxmlPath();
            if (fxmlPath == null || fxmlPath.isEmpty()) {
                System.err.println("FXML path not valid for: " + sceneType.name());
                continue;
            }

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent sceneRoot = fxmlLoader.load();
                GuiInputReaderController sceneController = fxmlLoader.getController();
                sceneCollection.add(new SceneClass(new Scene(sceneRoot), sceneType, sceneController));
            } catch (IOException e) {
                throw new RuntimeException("Error occurred while loading " + fxmlPath, e);
            }
        }
    }

    public void setActiveScene(SceneType sceneType) {
        isResized = false;
        int index = getSceneIndex(sceneType);
        if (index != -1) {
            SceneClass sceneInfo = sceneCollection.get(index);
            this.mainStage.setScene(sceneInfo.currentScene());
            this.mainStage.show();

            previousWidth = mainStage.getScene().getWidth();
            previousHeight = mainStage.getScene().getHeight();

            this.mainStage.widthProperty().addListener((obs, oldVal, newVal) -> resize((double) newVal - 30, previousHeight));
            this.mainStage.heightProperty().addListener((obs, oldVal, newVal) -> resize(previousWidth, (double) newVal - 60));

            isResized = true;
        }
    }

    public void setGUIReaderToScenes(LinkedBlockingQueue<String> guiReader) {
        for (SceneClass scene : sceneCollection) {
            scene.setInputReaderGUI(guiReader);
        }
    }

    public void resize(double width, double height) {
        if (isResized) {
            double w = width / previousWidth;
            double h = height / previousHeight;
            previousWidth = width;
            previousHeight = height;
            Scale scale = new Scale(w, h, 0, 0);
            mainStage.getScene().getRoot().getTransforms().add(scale);
        }
    }

    public GuiInputReaderController getSceneController(SceneType sceneType) {
        int index = getSceneIndex(sceneType);
        return index != -1 ? sceneCollection.get(index).sceneController() : null;
    }

    private int getSceneIndex(SceneType sceneType) {
        for (int i = 0; i < sceneCollection.size(); i++) {
            if (sceneCollection.get(i).sceneType() == sceneType) {
                return i;
            }
        }
        return -1;
    }

    public void newWindow() {
        Stage newStage = new Stage();
        newStage.setScene(this.mainStage.getScene());
        newStage.show();
        this.mainStage.close();
        this.mainStage = newStage;
        this.mainStage.centerOnScreen();
        this.mainStage.setAlwaysOnTop(true);

        this.mainStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");
            System.exit(0);
        });
    }

    //-----------------------------LOBBY------------------------------------------------------------------------


    private void showLobbyPlayerPane(String nickname, int playerIndex) {
        SceneType sceneType = switch (playerIndex) {
            case 0 -> SceneType.PLAYER_LOBBY_1;
            case 1 -> SceneType.PLAYER_LOBBY_2;
            case 2 -> SceneType.PLAYER_LOBBY_3;
            case 3 -> SceneType.PLAYER_LOBBY_4;
            default -> throw new IllegalArgumentException("Invalid player index: " + playerIndex);
        };

        int sceneIndex = getSceneIndex(sceneType);
        SceneClass sceneDetails = sceneCollection.get(sceneIndex);
        Pane newPaneRoot = (Pane) sceneDetails.currentScene().getRoot();

        PlayerLobbyController lobbyController = (PlayerLobbyController) sceneDetails.sceneController();
        lobbyController.setNickname(nickname);

        Pane targetPane = (Pane) this.mainStage.getScene().getRoot().lookup("#pane" + playerIndex);
        targetPane.setVisible(true);
        targetPane.getChildren().clear();

        newPaneRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        StackPane stackPaneWrapper = new StackPane();
        stackPaneWrapper.getChildren().add(newPaneRoot);
        StackPane.setAlignment(newPaneRoot, Pos.CENTER);

        newPaneRoot.prefWidthProperty().bind(targetPane.widthProperty());
        newPaneRoot.prefHeightProperty().bind(targetPane.heightProperty());

        targetPane.getChildren().add(stackPaneWrapper);
    }

    public void showPlayerToLobby(ModelView model) {
        hidePanesInLobby();
        int i = 0;
        for (Player p : model.getPlayersConnected()) {
            showLobbyPlayerPane(p.getNickname(), i);
            i++;
        }
    }
    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.mainStage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);
        }
    }
}
