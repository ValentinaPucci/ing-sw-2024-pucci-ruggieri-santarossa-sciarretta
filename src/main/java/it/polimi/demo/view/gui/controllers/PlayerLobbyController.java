package it.polimi.demo.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlayerLobbyController extends InputReaderController {
    @FXML private Text nickname;

    public void setNickname(String nickName){
        nickname.setText(nickName);
    }
}