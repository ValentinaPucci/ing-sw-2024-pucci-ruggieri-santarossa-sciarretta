package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.dynamic.utilities.parser.GuiReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController extends GenericController{

    @FXML
    public void CreateGame(ActionEvent event) {
        System.out.println("CreateGame: "+ event);
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.getBuffer().add("c");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void JoinGame(ActionEvent event) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.getBuffer().add("js");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void RandomGame(ActionEvent event) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.getBuffer().add("j");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void Reconnect(ActionEvent event) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.getBuffer().add("x");
        } else {
            // Gestione del caso in cui getInputReaderGUI() restituisce null
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

}
