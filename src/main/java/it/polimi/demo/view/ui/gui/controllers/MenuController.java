package it.polimi.demo.view.ui.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.concurrent.LinkedBlockingQueue;

public class MenuController extends GenericController{

    @FXML
    public void CreateGame(ActionEvent event) {
        System.out.println("CreateGame: "+ event);
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("c");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void JoinGame(ActionEvent event) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("js");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void RandomGame(ActionEvent event) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("j");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void Reconnect(ActionEvent event) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("x");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

}
