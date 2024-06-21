package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NicknameController extends GuiInputReaderController {

    @FXML private TextField nickname;

    @FXML
    void enter() {
        if(!nickname.getText().isEmpty()) {
            reader.add(nickname.getText());
        }
    }
}
