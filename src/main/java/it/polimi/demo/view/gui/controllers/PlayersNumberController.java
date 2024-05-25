package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class PlayersNumberController{

    @FXML
    private Button twoPlayers;

    @FXML
    private Button threePlayers;

    @FXML
    private Button fourPlayers;

    @FXML
    void GoToLobby(MouseEvent event) {


    }
/*
    public void actionEnter(ActionEvent e) throws IOException {
        if(!nickNameTextField.getText().isEmpty()) {
            getInputReaderGUI().addTxt(nickNameTextField.getText());
            //Sound.playSound("clickmenu.wav");
        }
    }*/

}
