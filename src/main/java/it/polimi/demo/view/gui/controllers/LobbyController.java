package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LobbyController extends GuiInputReaderController {

    @FXML public Text gameidLabel;
    @FXML private Button buttonReady;
    @FXML private Text nicknameLabel;
    @FXML
    void actionIamReady() {
        reader.add("y");
    }

    public void setGameId(int id) {
        gameidLabel.setText("GameID: "+id);
    }

    public void setNicknameLabel(String nickname) {
        nicknameLabel.setText(nickname);
    }

    public void setVisibleBtnReady(boolean visibility) {
        buttonReady.setVisible(visibility);
    }

}
