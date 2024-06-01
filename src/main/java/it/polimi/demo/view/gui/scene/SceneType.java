package it.polimi.demo.view.gui.scene;

public enum SceneType {
    MENU("/fxml/Menu.fxml"),
    NICKNAME("/fxml/InsertNickname.fxml"),
    LOBBY("/fxml/Lobby.fxml"),
    PLAYER_LOBBY_1("/fxml/PlayerLobby1.fxml"),
    PLAYER_LOBBY_2("/fxml/PlayerLobby2.fxml"),
    PLAYER_LOBBY_3("/fxml/PlayerLobby3.fxml"),
    PLAYER_LOBBY_4("/fxml/PlayerLobby4.fxml"),
    ID_GAME("/fxml/IDgame.fxml"),
    RUNNING("/fxml/RunningOLD.fxml"),
    GAME_OVER("/fxml/GameOver.fxml"),
    ERROR("/fxml/Error.fxml"),
    NUM_PLAYERS("/fxml/NumPlayers.fxml");

    private final String value;

    SceneType(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
