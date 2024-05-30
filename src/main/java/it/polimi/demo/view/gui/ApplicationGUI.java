package it.polimi.demo.view.gui;

import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.view.gui.controllers.*;
import it.polimi.demo.view.gui.scene.SceneType;
import it.polimi.demo.view.flow.utilities.inputReaderGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.demo.view.gui.scene.SceneType.*;



public class ApplicationGUI extends Application {
    private Stage primaryStage;
    private StackPane root;
    private ArrayList<SceneInfo> scenes;
    private double widthOld, heightOld;
    private boolean resizing = true;


    @Override
    public void start(Stage primaryStage) {
        try {
            loadScenes(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to start application.", e);
        }
    }

    private void loadScenes(Stage stage) throws IOException {
        // Adjust the path according to your FXML file location
        URL fxmlLocation = getClass().getResource("/fxml/Menu.fxml");
        System.out.println("FXML Location: " + fxmlLocation);

        if (fxmlLocation == null) {
            throw new IllegalStateException("Location is not set.");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method set the input reader GUI to all the controllers.
     * @param inputReaderGUI the input reader GUI {@link inputReaderGUI}
     */
    public void setInputReaderGUItoAllControllers(inputReaderGUI inputReaderGUI) {
        loadScenes();
        for (SceneInfo s : scenes) {
            s.setInputReaderGUI(inputReaderGUI);
        }
    }

    /**
     * This method use the FXMLLoader to load the scene and the controller of the scene.
     */
    private void loadScenes() {
        //Loads all the scenes available to be showed during the game
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController gc;
        for (int i = 0; i < SceneType.values().length; i++) {
            loader = new FXMLLoader(getClass().getResource(SceneType.values()[i].value()));
            try {
                root = loader.load();
                gc = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scenes.add(new SceneInfo(new Scene(root), SceneType.values()[i], gc));
        }
    }

    public void setActiveScene(SceneType sceneType) {
        SceneInfo sceneInfo = scenes.get(getSceneIndex(sceneType));
        if (sceneInfo != null) {
            primaryStage.setScene(sceneInfo.getScene());
            primaryStage.setTitle("CodexNaturalis - " + sceneType.name());
            primaryStage.centerOnScreen();
            primaryStage.setAlwaysOnTop(false);
            primaryStage.show();
        } else {
            throw new RuntimeException("Scene not found: " + sceneType.name());
        }
    }

    /**
     * This method is used to get a controller of a specific scene.
     *
     * @param scene the scene {@link SceneType}
     * @return the controller of the scene {@link GenericController}
     */
    public GenericController getController(SceneType scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return scenes.get(getSceneIndex(scene)).getGenericController();
        }
        return null;
    }

    /**
     * This method returns the index of the scene.
     *
     * @param sceneName the scene name {@link SceneType}
     * @return the index of the scene
     */
    private int getSceneIndex(SceneType sceneName) {
        for (int i = 0; i < scenes.size(); i++) {
            if (scenes.get(i).getSceneType().equals(sceneName))
                return i;
        }
        return -1;
    }


//
//
////    /**
////     * This method is used to set the active scene.
////     * @param scene the scene {@link SceneType}
////     */
//
////    //TODO: capire se devo inserire anche le scene di impostazione  del gioco (nickname/numPlayers/IDGame)
////    //TODO:capire se mi servono i popup
////    public void setActiveScene(SceneType scene) {
////        // Imposta il titolo della finestra principale includendo il nome della scena
////        this.primaryStage.setTitle("MyShelfie - " + scene.name());
////
////        // Indica che la finestra non è attualmente in fase di ridimensionamento
////        resizing = false;
////
////        // Ottiene l'indice della scena desiderata nella lista delle scene
////        int index = getSceneIndex(scene);
////
////        if (index != -1) {
////            SceneInfo s = scenes.get(getSceneIndex(scene));
////            switch (scene) {
////                case MENU -> {
////                    this.primaryStage.centerOnScreen();
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////                case NICKNAME -> {
////                    this.primaryStage.centerOnScreen();
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////                case ID_GAME -> {
////                    this.primaryStage.centerOnScreen();
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////                case PLAYERS_NUMBER -> {
////                    this.primaryStage.centerOnScreen();
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////                case LOBBY -> {
////                    this.primaryStage.centerOnScreen();
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////                case RUNNING -> {
////                    this.primaryStage.centerOnScreen();
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////                case GAME_OVER -> {
////                    this.primaryStage.centerOnScreen();
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////                default -> {
////                    // Imposta la finestra principale come non sempre in primo piano per i casi non specificati
////                    this.primaryStage.setAlwaysOnTop(false);
////                }
////            }
////
////            // Imposta la scena corrente sulla finestra principale e la mostra
////            this.primaryStage.setScene(s.getScene());
////            this.primaryStage.show();
////        }
////
////        // Memorizza le dimensioni attuali della scena
////        widthOld = primaryStage.getScene().getWidth();
////        heightOld = primaryStage.getScene().getHeight();
////
////        // Aggiunge un listener per il cambiamento della larghezza della finestra principale
////        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
////            rescale((double) newVal - 16, heightOld);
////        });
////
////        // Aggiunge un listener per il cambiamento dell'altezza della finestra principale
////        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
////            rescale(widthOld, (double) newVal - 39);
////        });
////
////        // Indica che la finestra può ora essere ridimensionata
////        resizing = true;
////    }
//
//    /**
//     * This method is used to rescale the scene.
//     */
//    public void rescale(double width, double heigh) {
//        if(resizing) {
//            double widthWindow = width;
//            double heightWindow = heigh;
//
//
//            double w = widthWindow / widthOld;  // your window width
//            double h = heightWindow / heightOld;  // your window height
//
//            widthOld = widthWindow;
//            heightOld = heightWindow;
//            Scale scale = new Scale(w, h, 0, 0);
//            //primaryStage.getScene().getRoot().getTransforms().add(scale);
//            primaryStage.getScene().lookup("#content").getTransforms().add(scale);
//        }
//    }
//
//    //----------metodo x marghe------------------------------------------------------------------------
//    /**
//     * This method is used to hide the panes in the lobby.
//     */
//    private void hidePanesInLobby() {
//        for (int i = 0; i < 4; i++) {
//            Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + i);
//            panePlayerLobby.setVisible(false);
//        }
//    }
//
//
//    //----------metodo x marghe------------------------------------------------------------------------
//    /**
//     * This method is used to show the player in the lobby.
//     * @param nick the nickname of the player
//     * @param indexPlayer the index of the player
//     * @param isReady if the player is ready
//     */
//    private void addLobbyPanePlayer(String nick, int indexPlayer, boolean isReady) {
//        SceneType se = null;
//        switch (indexPlayer) {
//            case 0 -> se = SceneType.PLAYER_LOBBY_1;
//            case 1 -> se = SceneType.PLAYER_LOBBY_2;
//            case 2 -> se = SceneType.PLAYER_LOBBY_3;
//            case 3 -> se = SceneType.PLAYER_LOBBY_4;
//        }
//
//        // Dichiarazione di una variabile per il pannello da caricare
//        Pane paneToLoad;
//
//        // Ottiene le informazioni della scena dall'elenco delle scene utilizzando il tipo di scena assegnato
//        SceneInfo sceneToLoad = scenes.get(getSceneIndex(se));
//
//        // Ottiene il nodo radice (root) della scena come un Pane
//        paneToLoad = (Pane) sceneToLoad.getScene().getRoot();
//
//        // Imposta il nickname del giocatore nel controller della scena caricata
//        ((PlayerLobbyController) sceneToLoad.getGenericController()).setNickname(nick);
//
//        // Ottiene il pannello della lobby per il giocatore corrente utilizzando l'ID del nodo
//        Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + indexPlayer);
//
//        // Rende visibile il pannello del giocatore nella lobby
//        panePlayerLobby.setVisible(true);
//
//        // Pulisce tutti i figli del pannello del giocatore
//        panePlayerLobby.getChildren().clear();
//
//        // Imposta le dimensioni massime del pannello da caricare
//        paneToLoad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//
//        // Crea uno StackPane e aggiunge il pannello da caricare ad esso
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(paneToLoad);
//
//        // Imposta l'allineamento del pannello da caricare al centro dello StackPane
//        StackPane.setAlignment(paneToLoad, Pos.CENTER);
//
//        // Vincola le proprietà di larghezza e altezza del pannello da caricare a quelle del pannello del giocatore
//        paneToLoad.prefWidthProperty().bind(panePlayerLobby.widthProperty());
//        paneToLoad.prefHeightProperty().bind(panePlayerLobby.heightProperty());
//
//        // Aggiunge lo StackPane al pannello del giocatore
//        panePlayerLobby.getChildren().add(stackPane);
//    }
//
//
//    //----------metodo x marghe-------------------------------------------------------------------------
//    /**
//     * Show the player in the lobby
//     * @param model the model {@link GameModelImmutable}
//     */
//    public void showPlayerToLobby(GameModelImmutable model) {
//        hidePanesInLobby();
//        int i = 0;
//        for (PlayerIC p : model.getPlayersConnected()) {
//            addLobbyPanePlayer(p.getNickname(), i, p.getReadyToStart());
//            i++;
//        }
//    }
//
//
//    /**
//     * This method hide the btn "Ready to start".
//     */
//    public void disableBtnReadyToStart() {
//        //I set not visible the btn "Ready to start"
//        ((LobbyController) scenes.get(getSceneIndex(SceneType.LOBBY)).getGenericController()).setVisibleBtnReady(false);
//    }
//
//
//    //chiamato da GUI
//    /**
//     * This method is used to set the controller value equal to the model value.
//     * @param model the model {@link GameModelImmutable}
//     * @param nickname the nickname of the player
//     */
//    public void showRunningModel(GameModelImmutable model, String nickname) {
//        RunningController controller = (RunningController) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
//        controller.setNicknamesAndPoints(model, nickname);
//        controller.setScoreBoard(model);
//        controller.setCommonCards(model);
//        controller.setPersonalObjective(model, nickname);
//        controller.setCardHand(model, nickname);
//        //TODO: controller.setPersonalBoard(model, nickname);
//        //TODO: controller.setOthersPersonalBoard(model, nickname);
//    }
//
//    /**
//     * This method is used to show the player grabbed tiles.
//     * @param model the model {@link GameModelImmutable}
//     * @param nickname the nickname of the player
//     */
//    public void showPlayerDrawnCard(GameModelImmutable model, String nickname) {
//        RunningController controller = (RunningController) scenes.get(getSceneIndex(RUNNING)).getGenericController();
//        controller.setPlayerDrawnCard(model, nickname);
//    }
//
//    /**
//     * This method is used to show the player positioned tiles.
//     * @param model the model {@link GameModelImmutable}
//     * @param nickname the nickname of the player
//     */
//    public void showPlayerPlacedCard(GameModelImmutable model, String nickname) {
//        RunningController controller = (RunningController) scenes.get(getSceneIndex(RUNNING)).getGenericController();
//        controller.setCardHand(model, nickname);
//        //TODO: controller.setPersonalBoard(model, nickname);
//    }
//
////    /**
////     * This method is used to change the turn.
////     * @param model the model {@link GameModelImmutable}
////     * @param nickname the nickname of the player
////     */
////    public void changeTurn(GameModelImmutable model, String nickname) {
////        RunningController controller = (RunningController) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
////        controller.setNicknamesAndPoints(model, nickname);
////        controller.changeTurn(model, nickname);
////    }
//
    /**
     * This method is used to show the message in the game.
     * @param model the model {@link GameModelImmutable}
     * @param myNickname the nickname of the player
     */
    public void showMessages(GameModelImmutable model, String myNickname) {
        RunningControllerOLD controller = (RunningControllerOLD) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
        controller.setMessage(model.getChat().getMsgs(), myNickname);
    }

    /**
     * This method is used to show all the important events.
     * @param importantEvents the list of the important events
     */
    public void showImportantEvents(List<String> importantEvents) {
        RunningControllerOLD controller = (RunningControllerOLD) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
        controller.setImportantEvents(importantEvents);
    }

    /**
     * This method is used to create a window with a specific style.
     */
    public void createNewWindowWithStyle() {
        // Crea una nuova finestra con lo stile desiderato
        Stage newStage = new Stage();

        // Copia la scena dalla finestra precedente
        newStage.setScene(this.primaryStage.getScene());

        // Mostra la nuova finestra
        newStage.show();

        // Chiudi la finestra precedente
        this.primaryStage.close();

        // Imposta la nuova finestra come primaryStage
        this.primaryStage = newStage;
        this.primaryStage.centerOnScreen();
        this.primaryStage.setAlwaysOnTop(true);

        this.primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");

            System.exit(1);
        });
    }

//
//
//
//    /**
//     * This method show the leader board.
//     * @param model the model {@link GameModelImmutable}
//     */
//    public void showLeaderBoard(GameModelImmutable model) {
//        GameOverController controller = (GameOverController) scenes.get(getSceneIndex(GAME_OVER)).getGenericController();
//        controller.show(model);
//    }
//
//    /**
//     * This method make visible the button return to menu.
//     */
//    public void showBtnReturnToMenu() {
//        GameOverController controller = (GameOverController) scenes.get(getSceneIndex(GAME_OVER)).getGenericController();
//        controller.showBtnReturnToMenu();
//    }
//
////    /**
////     * This method is used to show a generic error.
////     * @param msg the message
////     */
////    public void showErrorGeneric(String msg) {
////        ErrorController controller = (ErrorController) scenes.get(getSceneIndex(ERROR)).getGenericController();
////        controller.setMessage(msg,false);
////    }
//
    /**
     * This method is used to show a generic error.
     * @param msg the message
     * @param needToExitApp true if the app need to exit, false otherwise
     */
    public void showError(String msg, boolean needToExitApp) {
        ErrorController controller = (ErrorController) scenes.get(getSceneIndex(ERROR)).getGenericController();
    }
//
//    //    /**
////     * This method is used to show the points updated.
////     * @param model the model {@link GameModelImmutable}
////     * @param playerPointChanged the player that has changed the points
////     * @param myNickname the nickname of the player
////     * @param points the points
////     */
////    public void showPointsUpdated(GameModelImmutable model, Player playerPointChanged, String myNickname, int points) {
////        RunningController controller = (RunningController) scenes.get(getSceneIndex(SceneType.RUNNING)).getGenericController();
////        controller.setPointsUpdated(model, playerPointChanged, myNickname, points);
////    }
}
