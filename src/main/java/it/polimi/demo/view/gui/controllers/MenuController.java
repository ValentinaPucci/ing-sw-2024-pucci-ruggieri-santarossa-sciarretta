package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.concurrent.LinkedBlockingQueue;

public class MenuController extends GuiInputReaderController {

    @FXML
    public void CreateGame(ActionEvent event) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("c");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void JoinGame(ActionEvent event) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("js");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void RandomGame(ActionEvent event) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("j");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
}
