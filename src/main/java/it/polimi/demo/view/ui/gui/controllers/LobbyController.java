package it.polimi.demo.view.ui.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LobbyController extends GenericController {

    public Text gameidLabel;

    @FXML
    private Button buttonReady;

    @FXML
    private Text nicknameLabel;

    @FXML
    void actionIamReady(ActionEvent event) {
        getInputReaderGUI().add("y");
        System.out.println("READY!");
    }

    public void setGameId(int id) {
        gameidLabel.setText("GameID: "+id);
    }

    public void setNicknameLabel(String nickname){
        nicknameLabel.setText(nickname);
    }

    public void setVisibleBtnReady(boolean visibility){
        buttonReady.setVisible(visibility);
    }


}
