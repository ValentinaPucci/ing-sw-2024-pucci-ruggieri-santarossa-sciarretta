package it.polimi.demo.view.gui;

public enum SceneType {
    MENU("/fxml/Menu.fxml"),
    NICKNAME("/fxml/InsertNickname.fxml"),
    PLAYER_LOBBY_1("/fxml/PlayerLobby1.fxml"),
    PLAYER_LOBBY_2("/fxml/PlayerLobby2.fxml"),
    PLAYER_LOBBY_3("/fxml/PlayerLobby3.fxml"),
    PLAYER_LOBBY_4("/fxml/PlayerLobby4.fxml"),
    LOBBY("/fxml/Lobby.fxml"),
    ID_GAME("/fxml/IDgame.fxml"),
    RUNNING("/fxml/Running.fxml"),
    GAME_OVER("/fxml/GameOver.fxml"),
    ERROR("/fxml/Error.fxml"),
    NUM_PLAYERS("/fxml/NumPlayers.fxml");

    private final String fxmlPath;

    SceneType(final String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
