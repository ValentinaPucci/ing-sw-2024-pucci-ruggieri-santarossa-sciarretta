package it.polimi.demo.view.gui;

import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.interfaces.PlayerIC;
import it.polimi.demo.view.flow.ConnectionSelection;
import it.polimi.demo.view.flow.GameFlow;
import it.polimi.demo.view.gui.controllers.*;
import it.polimi.demo.view.gui.scene.SceneType;
import it.polimi.demo.view.flow.utilities.inputReaderGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.demo.view.gui.scene.SceneType.*;



public class ApplicationGUI extends Application {
    private Stage primaryStage;
    private StackPane root;
    private GameFlow gameFlow;
    private ArrayList<SceneInfo> scenes;
    private double widthOld, heightOld;
    private boolean resizing = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        gameFlow = new GameFlow(this, ConnectionSelection.valueOf(getParameters().getUnnamed().get(0)));
        loadScenes();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Codex Naturalis");

        root = new StackPane();
    }

    private void loadScenes() {
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController gc;

        for (int i = 0; i < SceneType.values().length; i++) {
            String fxmlPath = SceneType.values()[i].value();
            if (fxmlPath == null || fxmlPath.isEmpty()) {
                System.err.println("Percorso FXML non impostato per: " + SceneType.values()[i]);
                continue;
            }
            loader = new FXMLLoader(getClass().getResource(fxmlPath));
            try {
                root = loader.load();
                gc = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException("Errore nel caricamento di " + fxmlPath, e);
            }
            scenes.add(new SceneInfo(new Scene(root), SceneType.values()[i], gc));
        }
    }

    public void setActiveScene(SceneType scene) {
        this.primaryStage.setTitle("Codex Naturalis - "+scene.name());
        resizing=false;
        int index = getSceneIndex(scene);
        if (index != -1) {
            SceneInfo s = scenes.get(getSceneIndex(scene));
            this.primaryStage.setScene(s.getScene());
            this.primaryStage.show();
        }

        widthOld=primaryStage.getScene().getWidth();
        heightOld=primaryStage.getScene().getHeight();
        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            rescale((double)newVal-16,heightOld);
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            rescale(widthOld,(double)newVal-39);
        });
        resizing=true;

        InputStream logoStream = ApplicationGUI.class.getClassLoader().getResourceAsStream("logo.png");
        System.out.println(logoStream); // Stampa per debug
        if (logoStream != null) {
            primaryStage.getIcons().add(new Image(logoStream));
        } else {
            System.err.println("Impossibile trovare logo.png");
        }
    }

    public void setInputReaderGUItoAllControllers(inputReaderGUI inputReaderGUI) {
        loadScenes();
        for (SceneInfo s : scenes) {
            s.setInputReaderGUI(inputReaderGUI);
        }
        System.out.println("setInputReaderGUItoAllControllers");
    }

    public void rescale(double width, double heigh) {
        if(resizing) {
            double widthWindow = width;
            double heightWindow = heigh;


            double w = widthWindow / widthOld;  // your window width
            double h = heightWindow / heightOld;  // your window height

            widthOld = widthWindow;
            heightOld = heightWindow;
            Scale scale = new Scale(w, h, 0, 0);
            //primaryStage.getScene().getRoot().getTransforms().add(scale);
            primaryStage.getScene().lookup("#content").getTransforms().add(scale);
        }
    }

    public GenericController getController(SceneType scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return scenes.get(getSceneIndex(scene)).getGenericController();
        }
        return null;
    }

    private int getSceneIndex(SceneType sceneName) {
        for (int i = 0; i < scenes.size(); i++) {
            if (scenes.get(i).getSceneType().equals(sceneName))
                return i;
        }
        return -1;
    }

    public void createNewWindowWithStyle() {
        Stage newStage = new Stage();
        newStage.setScene(this.primaryStage.getScene());
        newStage.show();
        this.primaryStage.close();
        this.primaryStage = newStage;
        this.primaryStage.centerOnScreen();
        this.primaryStage.setAlwaysOnTop(true);

        this.primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");

            System.exit(1);
        });
    }



    //-----------------------------LOBBY------------------------------------------------------------------------
    /**
     * This method is used to hide the panes in the lobby.
     */
    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);
        }
    }


    /**
     * This method is used to show the player in the lobby.
     * @param nick the nickname of the player
     * @param indexPlayer the index of the player
     * @param isReady if the player is ready
     */
    private void addLobbyPanePlayer(String nick, int indexPlayer, boolean isReady) {
        SceneType se = null;
        switch (indexPlayer) {
            case 0 -> se = SceneType.PLAYER_LOBBY_1;
            case 1 -> se = SceneType.PLAYER_LOBBY_2;
            case 2 -> se = SceneType.PLAYER_LOBBY_3;
            case 3 -> se = SceneType.PLAYER_LOBBY_4;
        }
        Pane paneToLoad;
        SceneInfo sceneToLoad = scenes.get(getSceneIndex(se));
        paneToLoad = (Pane) sceneToLoad.getScene().getRoot();
        ((PlayerLobbyController) sceneToLoad.getGenericController()).setNickname(nick);
        Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + indexPlayer);
        panePlayerLobby.setVisible(true);
        panePlayerLobby.getChildren().clear();
        paneToLoad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(paneToLoad);
        StackPane.setAlignment(paneToLoad, Pos.CENTER);
        paneToLoad.prefWidthProperty().bind(panePlayerLobby.widthProperty());
        paneToLoad.prefHeightProperty().bind(panePlayerLobby.heightProperty());
        panePlayerLobby.getChildren().add(stackPane);
    }

    public void showPlayerToLobby(GameModelImmutable model) {
        hidePanesInLobby();
        int i = 0;
        for (PlayerIC p : model.getPlayersConnected()) {
            addLobbyPanePlayer(p.getNickname(), i, p.getReadyToStart());
            i++;
        }
    }

    public void disableBtnReadyToStart() {
        ((LobbyController) scenes.get(getSceneIndex(SceneType.LOBBY)).getGenericController()).setVisibleBtnReady(false);
    }

    //---------------------------------RUNNING----------------------------------------------


    public void changeTurn(GameModelImmutable model, String nickname) {
        RunningController controller = (RunningController) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
        controller.setPlayersPointsAndNicknames(model, nickname);
        controller.changeTurn(model, nickname);
    }

    public void showRunningModel(GameModelImmutable model, String nickname) {
        RunningController controller = (RunningController) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
        controller.setCardHand(model, nickname);
        controller.setStarterCardFront(model, nickname);
        controller.setScoreBoardPosition(model);
        controller.setPlayersPointsAndNicknames(model, nickname);
        controller.setCommonCards(model);
        controller.setPersonalObjectives(model, nickname);
        //TODO: controller.setPersonalBoard(model, nickname);
        //TODO: controller.setOthersPersonalBoard(model, nickname);
    }

    public void showPlayerDrawnCard(GameModelImmutable model, String nickname) {
        RunningController controller = (RunningController) scenes.get(getSceneIndex(RUNNING)).getGenericController();
        controller.setPlayerDrawnCard(model, nickname);
    }

    public void showPlayerPlacedCard(GameModelImmutable model, String nickname) {
        RunningController controller = (RunningController) scenes.get(getSceneIndex(RUNNING)).getGenericController();
        controller.setCardHand(model, nickname);
        //TODO: controller.setPersonalBoard(model, nickname);
    }

    //-------------------------------------CHAT, EVENTI E MESSAGI------------------------------------


    public void showErrorGeneric(String msg) {
        ErrorController controller = (ErrorController) scenes.get(getSceneIndex(SceneType.ERROR)).getGenericController();
        controller.setMessage(msg,false);
    }

//TODO: understand what to do

//    public void showMessages(GameModelImmutable model, String myNickname) {
//        RunningController controller = (RunningController) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
//        controller.setMessage(model.getChat().getMsgs(), myNickname);
//    }

    public void showImportantEvents(List<String> importantEvents) {
        RunningController controller = (RunningController) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
        controller.setImportantEvents(importantEvents);
    }


    public void showError(String msg) {
        ErrorController controller = (ErrorController) scenes.get(getSceneIndex(ERROR)).getGenericController();
    }

}
