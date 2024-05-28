package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GameIdController extends GenericController{

    @FXML
    private TextField GameId;

    @FXML
    void EnterGame(ActionEvent event) {
        if(!GameId.getText().isEmpty()){
            getInputReaderGUI().addTxt(GameId.getText());
        }

    }

}
