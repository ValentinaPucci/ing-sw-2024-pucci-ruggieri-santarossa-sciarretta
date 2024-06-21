package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GameIdController extends GuiInputReaderController {

    @FXML
    private TextField GameId;

    @FXML
    void EnterGame() {
        if (!GameId.getText().isEmpty()) {
            reader.add(GameId.getText());
        }
    }
}
