package it.polimi.demo.view.gui;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.view.dynamic.utilities.TypeConnection;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.view.gui.controllers.*;

import it.polimi.demo.view.gui.utils.SceneClass;
import it.polimi.demo.view.gui.utils.SceneType;
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
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * JavaFX app class for managing scenes and dynamic content.
 */
public class FXApplication extends Application {

    private Stage mainStage;
    private List<SceneClass> sceneCollection;
    private double previousWidth, previousHeight;
    private boolean isResized = true;

    /**
     * Initializes and starts the application.
     *
     * @param mainStage the primary stage for this application
     */
    @Override
    public void start(Stage mainStage) {
        new GameDynamic(this, TypeConnection.valueOf(getParameters().getUnnamed().getFirst()));
        this.mainStage = mainStage;
        initializeScenes();
    }

    /**
     * Initializes scenes by loading them from FXML files.
     */
    private void initializeScenes() {
        sceneCollection = Arrays.stream(SceneType.values())
                .map(this::loadScene)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    /**
     * Loads a scene from its FXML path.
     *
     * @param sceneType the type of scene to load
     * @return an Optional containing the loaded SceneClass, or empty if loading failed
     */
    private Optional<SceneClass> loadScene(SceneType sceneType) {
        String fxmlPath = sceneType.getFxmlPath();
        if (fxmlPath == null || fxmlPath.isEmpty()) {
            System.err.println("FXML path not valid for: " + sceneType.name());
            return Optional.empty();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent sceneRoot = fxmlLoader.load();
            GuiInputReaderController sceneController = fxmlLoader.getController();
            return Optional.of(new SceneClass(new Scene(sceneRoot), sceneType, sceneController));
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while loading " + fxmlPath, e);
        }
    }

    /**
     * Sets the active scene.
     *
     * @param sceneType the type of scene to activate
     */
    public void setActiveScene(SceneType sceneType) {
        getSceneByType(sceneType).ifPresent(sceneInfo -> {
            isResized = false;
            mainStage.setScene(sceneInfo.currentScene());
            mainStage.show();
            updateDimensionsHW();
            updateDimensionsMainStage();
            isResized = true;
        });
    }

    /**
     * Updates the dimensions of the main stage.
     */
    public void updateDimensionsHW() {
        previousHeight = mainStage.getScene().getHeight();
        previousWidth = mainStage.getScene().getWidth();
    }

    /**
     * Updates the dimensions of the main stage.
     */
    public void updateDimensionsMainStage() {
        mainStage.widthProperty().addListener((obs, oldVal, newVal) -> resize((double) newVal - 30, previousHeight));
        mainStage.heightProperty().addListener((obs, oldVal, newVal) -> resize(previousWidth, (double) newVal - 60));
    }

    /**
     * Sets the GUI reader for all scenes.
     *
     * @param guiReader the GUI reader
     */
    public void setGUIReaderToScenes(LinkedBlockingQueue<String> guiReader) {
        sceneCollection.forEach(scene -> scene.setInputReaderGUI(guiReader));
    }

    /**
     * Resizes the scene.
     *
     * @param width  the new width
     * @param height the new height
     */
    public void resize(double width, double height) {
        if (isResized) {
            previousWidth = width;
            previousHeight = height;
            Scale scale = new Scale(width / previousWidth, height / previousHeight, 0, 0);
            mainStage.getScene().getRoot().getTransforms().add(scale);
        }
    }

    /**
     * Retrieves the scene controller for a given scene type.
     *
     * @param sceneType the type of scene
     * @return the scene controller, or null if not found
     */
    public GuiInputReaderController getSceneController(SceneType sceneType) {
        return getSceneByType(sceneType).map(SceneClass::sceneController).orElse(null);
    }

    /**
     * Retrieves a SceneClass by its type.
     *
     * @param sceneType the type of scene
     * @return an Optional containing the SceneClass, or empty if not found
     */
    private Optional<SceneClass> getSceneByType(SceneType sceneType) {
        return sceneCollection.stream()
                .filter(scene -> scene.sceneType() == sceneType)
                .findFirst();
    }

    /**
     * Opens a new window with the current scene.
     */
    public void newWindow() {
        Stage newStage = createNewStageWithCurrentScene();
        configureNewStage(newStage);
        closeCurrentStageAndSetNew(newStage);
    }

    /**
     * Creates a new stage with the current scene.
     *
     * @return the new stage
     */
    private Stage createNewStageWithCurrentScene() {
        Stage newStage = new Stage();
        newStage.setScene(mainStage.getScene());
        return newStage;
    }

    /**
     * Configures the new stage.
     *
     * @param newStage the new stage to configure
     */
    private void configureNewStage(Stage newStage) {
        newStage.centerOnScreen();
        newStage.setAlwaysOnTop(true);
        newStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");
            System.exit(0);
        });
        newStage.show();
    }

    /**
     * Closes the current stage and sets the new stage as the main stage.
     *
     * @param newStage the new stage
     */
    private void closeCurrentStageAndSetNew(Stage newStage) {
        mainStage.close();
        mainStage = newStage;
    }

    private final Map<Integer, SceneType> playerIndexToSceneTypeMap = Map.of(
            0, SceneType.PLAYER_LOBBY_1,
            1, SceneType.PLAYER_LOBBY_2,
            2, SceneType.PLAYER_LOBBY_3,
            3, SceneType.PLAYER_LOBBY_4
    );

    /**
     * Shows the lobby player pane for a given player index and nickname.
     *
     * @param nickname    the player's nickname
     * @param playerIndex the player's index
     */
    private void showLobbyPlayerPane(String nickname, int playerIndex) {
        Optional.ofNullable(playerIndexToSceneTypeMap.get(playerIndex))
                .flatMap(this::getSceneByType)
                .ifPresentOrElse(sceneDetails -> setupSceneForPlayer(sceneDetails, nickname, playerIndex),
                        () -> {
                            throw new IllegalArgumentException("Invalid player index: " + playerIndex);
                        });
    }

    /**
     * Sets up the scene for a player.
     *
     * @param sceneDetails the scene details
     * @param nickname     the player's nickname
     * @param playerIndex  the player's index
     */
    private void setupSceneForPlayer(SceneClass sceneDetails, String nickname, int playerIndex) {
        Pane newPaneRoot = (Pane) sceneDetails.currentScene().getRoot();
        PlayerLobbyController lobbyController = (PlayerLobbyController) sceneDetails.sceneController();
        lobbyController.setNickname(nickname);

        Pane targetPane = (Pane) mainStage.getScene().getRoot().lookup("#pane" + playerIndex);
        if (targetPane != null) {
            setupPane(targetPane, newPaneRoot);
        }
    }

    /**
     * Sets up a pane with a new root.
     *
     * @param targetPane  the target pane
     * @param newPaneRoot the new pane root
     */
    private void setupPane(Pane targetPane, Pane newPaneRoot) {
        Runnable clearAndShowPane = () -> {
            targetPane.getChildren().clear();
            targetPane.setVisible(true);
        };

        clearAndShowPane.run();

        newPaneRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        StackPane.setAlignment(newPaneRoot, Pos.CENTER);

        bindProperties(targetPane, newPaneRoot);

        targetPane.getChildren().add(new StackPane(newPaneRoot));
    }

    /**
     * Binds properties between target pane and new pane root.
     *
     * @param targetPane  the target pane
     * @param newPaneRoot the new pane root
     */
    private void bindProperties(Pane targetPane, Pane newPaneRoot) {
        newPaneRoot.prefWidthProperty().bind(targetPane.widthProperty());
        newPaneRoot.prefHeightProperty().bind(targetPane.heightProperty());
    }

    /**
     * Shows players in the lobby based on the given model view.
     *
     * @param model the model view
     */
    public void showPlayerToLobby(ModelView model) {
        hidePanesInLobby();
        IntStream.range(0, model.getPlayersConnected().size())
                .forEach(i -> showLobbyPlayerPane(model.getPlayersConnected().get(i).getNickname(), i));
    }

    /**
     * Hides all player panes in the lobby.
     */
    private void hidePanesInLobby() {
        IntStream.range(0, 4).forEach(i -> {
            Pane panePlayerLobby = (Pane) mainStage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);
        });
    }
}


