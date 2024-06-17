package it.polimi.demo.view.ui.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GameIdController extends GenericController{

    @FXML
    private TextField GameId;

    @FXML
    void EnterGame(ActionEvent event) {
        System.out.println("GameId entered:" + GameId + GameId.getText());
        if(!GameId.getText().isEmpty()){
            getInputReaderGUI().add(GameId.getText());
        }

    }

}
