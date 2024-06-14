package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.dynamic.utilities.liveBuffer.ReaderQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class numPlayersController extends GenericController {

    @FXML
    private Button numPlayers2;

    @FXML
    private Button numPlayers3;

    @FXML
    private Button numPlayers4;

    @FXML
    public void twoPLayers(ActionEvent actionEvent) {
        ReaderQueue reader = getInputReaderGUI();
        if (reader != null) {
            reader.getBuffer().add("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void threePLayers(ActionEvent actionEvent) {
        ReaderQueue reader = getInputReaderGUI();
        if (reader != null) {
            reader.getBuffer().add("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
    @FXML
    public void fourPLayers(ActionEvent actionEvent) {
        ReaderQueue reader = getInputReaderGUI();
        if (reader != null) {
            reader.getBuffer().add("4");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

}


