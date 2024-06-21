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


public class ApplicationGUI extends Application {

    private Stage mainStage;
    private StackPane rootContainer;
    private GameDynamic gameDynamics;
    private ArrayList<SceneClass> sceneCollection;
    private double previousWidth, previousHeight;
    private boolean isResized = true;

    @Override
    public void start(Stage mainStage) {
        gameDynamics = new GameDynamic(this, TypeConnection.valueOf(getParameters().getUnnamed().getFirst()));
        initializeScenes();
        this.mainStage = mainStage;
        this.mainStage.setTitle("Codex Naturalis");
        rootContainer = new StackPane();
    }

    private void initializeScenes() {
        sceneCollection = new ArrayList<>();
        FXMLLoader fxmlLoader;
        Parent sceneRoot;
        SceneController sceneController;

        for (SceneType sceneType : SceneType.values()) {
            String fxmlPath = sceneType.getFxmlPath();
            if (fxmlPath == null || fxmlPath.isEmpty()) {
                System.err.println("Percorso FXML non impostato per: " + sceneType.getFxmlPath());
                continue;
            }
            fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            try {
                sceneRoot = fxmlLoader.load();
                sceneController = fxmlLoader.getController();
            } catch (IOException e) {
                throw new RuntimeException("Errore nel caricamento di " + fxmlPath, e);
            }
            sceneCollection.add(new SceneClass(new Scene(sceneRoot), sceneType, sceneController));
        }
    }

    public void setActiveScene(SceneType scene) {
        this.mainStage.setTitle("Codex Naturalis - "+scene.name());
        isResized = false;
        int index = getSceneIndex(scene);
        if (index != -1) {
            SceneClass sceneInfo = sceneCollection.get(index);
            this.mainStage.setScene(sceneInfo.getCurrentScene());
            this.mainStage.show();
        }

        previousWidth = mainStage.getScene().getWidth();
        previousHeight = mainStage.getScene().getHeight();
        this.mainStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            rescale((double)newVal-16, previousHeight);
        });

        this.mainStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            rescale(previousWidth,(double)newVal-39);
        });
        isResized =true;
    }

    public void assignGUIReaderToControllers(LinkedBlockingQueue<String> GuiReader) {
        initializeScenes();
        for (SceneClass sceneCollection : sceneCollection) {
            sceneCollection.setInputReaderGUI(GuiReader);
        }
    }

    public void rescale(double width, double heigh) {
        if(isResized) {
            double w = width / previousWidth;
            double h = heigh / previousHeight;
            previousWidth = width;
            previousHeight = heigh;
            Scale scale = new Scale(w, h, 0, 0);
            mainStage.getScene().getRoot().getTransforms().add(scale);
        }
    }

    public SceneController getSceneController(SceneType scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return sceneCollection.get(index).getSceneController();
        }
        return null;
    }

    private int getSceneIndex(SceneType sceneName) {
        for (int i = 0; i < sceneCollection.size(); i++) {
            if (sceneCollection.get(i).getSceneType() == sceneName)
                return i;
        }
        return -1;
    }

    public void createNewWindowWithStyle() {
        Stage newStage = new Stage();
        newStage.setScene(this.mainStage.getScene());
        newStage.show();
        this.mainStage.close();
        this.mainStage = newStage;
        this.mainStage.centerOnScreen();
        this.mainStage.setAlwaysOnTop(true);

        this.mainStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");
            System.exit(1);
        });
    }

    //-----------------------------LOBBY------------------------------------------------------------------------
    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.mainStage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);
        }
    }

    private void showLobbyPlayerPane(String nick, int playerIndex) {
        SceneType sceneType = null;
        switch (playerIndex) {
            case 0 -> sceneType = SceneType.PLAYER_LOBBY_1;
            case 1 -> sceneType = SceneType.PLAYER_LOBBY_2;
            case 2 -> sceneType = SceneType.PLAYER_LOBBY_3;
            case 3 -> sceneType = SceneType.PLAYER_LOBBY_4;
        }
        Pane newPane;
        SceneClass sceneToLoad = sceneCollection.get(getSceneIndex(sceneType));
        newPane = (Pane) sceneToLoad.getCurrentScene().getRoot();
        ((PlayerLobbyController) sceneToLoad.getSceneController()).setNickname(nick);
        Pane panePlayerLobby = (Pane) this.mainStage.getScene().getRoot().lookup("#pane" + playerIndex);
        panePlayerLobby.setVisible(true);
        panePlayerLobby.getChildren().clear();
        newPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(newPane);
        StackPane.setAlignment(newPane, Pos.CENTER);
        newPane.prefWidthProperty().bind(panePlayerLobby.widthProperty());
        newPane.prefHeightProperty().bind(panePlayerLobby.heightProperty());
        panePlayerLobby.getChildren().add(stackPane);
    }

    public void showPlayerToLobby(ModelView model) {
        hidePanesInLobby();
        int i = 0;
        for (Player p : model.getPlayersConnected()) {
            showLobbyPlayerPane(p.getNickname(), i);
            i++;
        }
    }

    public void disableBtnReadyToStart() {
        ((LobbyController) sceneCollection.get(getSceneIndex(SceneType.LOBBY)).getSceneController()).setVisibleBtnReady(false);
    }
}
