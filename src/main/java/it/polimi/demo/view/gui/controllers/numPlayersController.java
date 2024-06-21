package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.concurrent.LinkedBlockingQueue;

public class numPlayersController extends InputReaderController {

    @FXML
    public void twoPLayers(ActionEvent actionEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void threePLayers(ActionEvent actionEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
    @FXML
    public void fourPLayers(ActionEvent actionEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("4");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

}


