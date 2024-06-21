package it.polimi.demo.view.gui;

import java.util.EnumMap;
import java.util.Map;

/**
 * Enum representing different types of scenes in the GUI, each associated with a specific FXML file path.
 */
public enum SceneType {
    MENU,
    NICKNAME,
    PLAYER_LOBBY_1,
    PLAYER_LOBBY_2,
    PLAYER_LOBBY_3,
    PLAYER_LOBBY_4,
    LOBBY,
    ID_GAME,
    RUNNING,
    GAME_OVER,
    ERROR,
    NUM_PLAYERS;

    private static final Map<SceneType, String> fxmlPaths = new EnumMap<>(SceneType.class);

    static {
        fxmlPaths.put(MENU, "/fxml/Menu.fxml");
        fxmlPaths.put(NICKNAME, "/fxml/InsertNickname.fxml");
        fxmlPaths.put(PLAYER_LOBBY_1, "/fxml/PlayerLobby1.fxml");
        fxmlPaths.put(PLAYER_LOBBY_2, "/fxml/PlayerLobby2.fxml");
        fxmlPaths.put(PLAYER_LOBBY_3, "/fxml/PlayerLobby3.fxml");
        fxmlPaths.put(PLAYER_LOBBY_4, "/fxml/PlayerLobby4.fxml");
        fxmlPaths.put(LOBBY, "/fxml/Lobby.fxml");
        fxmlPaths.put(ID_GAME, "/fxml/IDgame.fxml");
        fxmlPaths.put(RUNNING, "/fxml/Running.fxml");
        fxmlPaths.put(GAME_OVER, "/fxml/GameOver.fxml");
        fxmlPaths.put(ERROR, "/fxml/Error.fxml");
        fxmlPaths.put(NUM_PLAYERS, "/fxml/NumPlayers.fxml");
    }

    /**
     * Gets the FXML file path associated with the scene type.
     *
     * @return The FXML file path.
     */
    public String getFxmlPath() {
        return fxmlPaths.get(this);
    }
}



