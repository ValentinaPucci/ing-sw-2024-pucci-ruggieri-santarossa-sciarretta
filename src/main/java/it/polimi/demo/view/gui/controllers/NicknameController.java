package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NicknameController extends GenericController {

    @FXML
    private TextField nickname;

    @FXML
    void enter(ActionEvent event) {
        if(!nickname.getText().isEmpty()) {
            getInputReaderGUI().getBuffer().add(nickname.getText());
        }
    }


}
