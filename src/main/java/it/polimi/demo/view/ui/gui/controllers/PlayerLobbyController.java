package it.polimi.demo.view.ui.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlayerLobbyController extends GenericController {
    @FXML
    private Text nickname;

    /**
     * Method to set the nickname.
     * @param nickName the nickname
     */
    public void setNickname(String nickName){
        nickname.setText(nickName);
    }
}