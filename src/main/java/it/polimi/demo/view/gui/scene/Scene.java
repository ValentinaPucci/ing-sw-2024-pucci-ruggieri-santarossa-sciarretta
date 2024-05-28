package it.polimi.demo.view.gui.scene;

public enum Scene {

    MENU("/Menu.fxml"),
    NICKNAME("/InsertNickname.fxml"),
    LOBBY("/Lobby.fxml"),
    PLAYER_LOBBY_1("/PlayerLobby1.fxml"),
    PLAYER_LOBBY_2("/PlayerLobby2.fxml"),
    PLAYER_LOBBY_3("/PlayerLobby3.fxml"),
    PLAYER_LOBBY_4("/PlayerLobby4.fxml"),
    ID_GAME("/IDgame.fxml"),
    //INGAME("/InGame.fxml"),
    GAME_OVER("/GameOver.fxml"),
    PLAYERS_NUMBER("PlayersNumber"),
    ERROR("/Error.fxml");



    private final String value;


    Scene(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
