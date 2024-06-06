package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.flow.utilities.GuiReader;
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
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("2");
        } else {
            System.out.println("L'oggetto GuiReader è null.");
        }
    }

    @FXML
    public void threePLayers(ActionEvent actionEvent) {
        System.out.println("PlayerSelection: threePLayers "+ actionEvent);
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("3");
        } else {
            System.out.println("L'oggetto GuiReader è null.");
        }
    }
    @FXML
    public void fourPLayers(ActionEvent actionEvent) {
        System.out.println("PlayerSelection: fourPLayers "+ actionEvent);
        it.polimi.demo.view.flow.utilities.GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("4");
        } else {
            System.out.println("L'oggetto GuiReader è null.");
        }
    }

}


