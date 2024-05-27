package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController extends GenericController{

    @FXML
    public void CreateGame(ActionEvent event) {
        getInputReaderGUI().addTxt("c");

    }

    @FXML
    public void JoinGame(ActionEvent event) {
        getInputReaderGUI().addTxt("js");
    }

    @FXML
    public void RandomGame(ActionEvent event) {
        getInputReaderGUI().addTxt("j");
    }

    @FXML
    public void Reconnect(ActionEvent event) {
        getInputReaderGUI().addTxt("x");

    }

}
