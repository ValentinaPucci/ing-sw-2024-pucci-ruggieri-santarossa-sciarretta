package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LobbyController extends GenericController {

    @FXML
    private Button buttonReady;

    @FXML
    private Text nicknameLabel;

    @FXML
    void actionIamReady(ActionEvent event) {
        getInputReaderGUI().addTxt("y");
    }

    public void setNicknameLabel(String nickname){
        nicknameLabel.setText(nickname);
    }

    public void setVisibleBtnReady(boolean visibility){
        buttonReady.setVisible(visibility);
    }


}
