package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.gui.GuiInputReaderController;
import javafx.fxml.FXML;

public class numPlayersController extends GuiInputReaderController {

    @FXML
    public void twoPLayers() {
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void threePLayers() {
        if (reader != null) {
            reader.add("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
    @FXML
    public void fourPLayers() {
        if (reader != null) {
            reader.add("4");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

}


