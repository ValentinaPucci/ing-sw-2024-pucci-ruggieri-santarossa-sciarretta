package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlayerLobbyController extends GuiInputReaderController {
    @FXML private Text nickname;

    public void setNickname(String nickName){
        nickname.setText(nickName);
    }
}