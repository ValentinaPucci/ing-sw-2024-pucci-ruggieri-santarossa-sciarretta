package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ErrorController extends GenericController {

    @FXML
    private Button button;

    @FXML
    private TextArea message;

    //private boolean needToExitApp;

    public void GoToMenu(ActionEvent event) {
        getInputReaderGUI().addTxt("a");
    }

    public void setMessage(String message){
        this.message.setText(message);
        //attenzione al fatto che può essersi disconnesso
    }

}
