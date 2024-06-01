package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.flow.utilities.inputReaderGUI;
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
        System.out.println("PlayerSelection: twoPLayers "+ actionEvent);
        inputReaderGUI reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void threePLayers(ActionEvent actionEvent) {
        System.out.println("PlayerSelection: threePLayers "+ actionEvent);
        inputReaderGUI reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
    @FXML
    public void fourPLayers(ActionEvent actionEvent) {
        System.out.println("PlayerSelection: fourPLayers "+ actionEvent);
        inputReaderGUI reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

}


