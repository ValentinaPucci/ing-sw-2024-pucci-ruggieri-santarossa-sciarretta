package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LobbyController extends GuiInputReaderController {

    @FXML public Text gameIdLabel;
    @FXML private Button buttonReady;
    @FXML private Text nicknameLabel;

    @FXML
    void actionIamReady() {
        reader.add("y");
    }

    public void setGameId(int id) {
        gameIdLabel.setText("GameID: " + id);
    }

    public void setNicknameLabel(String nick) {
        nicknameLabel.setText(nick);
    }

    public void setVisibleBtnReady(boolean bool) {
        buttonReady.setVisible(bool);
    }
}
