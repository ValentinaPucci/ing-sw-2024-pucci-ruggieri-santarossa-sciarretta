package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.fxml.FXML;

public class MenuController extends GuiInputReaderController {

    @FXML
    public void CreateGame() {
        if (reader != null) {
            reader.add("c");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void JoinGame() {
        if (reader != null) {
            reader.add("js");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void RandomGame() {
        if (reader != null) {
            reader.add("j");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
}
