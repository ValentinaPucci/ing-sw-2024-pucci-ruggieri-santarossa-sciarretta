package it.polimi.demo.view.gui.utils;

/**
 * Enum representing different types of scenes in the GUI, each associated with a specific FXML file path.
 */
public enum SceneType {
    MENU(new Scene("/fxml/Menu.fxml")),
    NICKNAME(new Scene("/fxml/InsertNickname.fxml")),
    PLAYER_LOBBY_1(new Scene("/fxml/PlayerLobby1.fxml")),
    PLAYER_LOBBY_2(new Scene("/fxml/PlayerLobby2.fxml")),
    PLAYER_LOBBY_3(new Scene("/fxml/PlayerLobby3.fxml")),
    PLAYER_LOBBY_4(new Scene("/fxml/PlayerLobby4.fxml")),
    LOBBY(new Scene("/fxml/Lobby.fxml")),
    ID_GAME(new Scene("/fxml/IDgame.fxml")),
    RUNNING(new Scene("/fxml/Running.fxml")),
    GAME_OVER(new Scene("/fxml/GameOver.fxml")),
    ERROR(new Scene("/fxml/Error.fxml")),
    NUM_PLAYERS(new Scene("/fxml/NumPlayers.fxml"));

    private final Scene scene;

    SceneType(Scene scene) {
        this.scene = scene;
    }

    /**
     * Gets the FXML file path associated with the scene type.
     *
     * @return The FXML file path.
     */
    public String getFxmlPath() {
        return scene.fxmlPath();
    }
}






