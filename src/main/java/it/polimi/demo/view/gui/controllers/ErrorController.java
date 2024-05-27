package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ErrorController {

    @FXML
    private Button button;

    @FXML
    private TextArea message;

    @FXML //perchè compare???
    public void GoToMenu(ActionEvent event) {
       //prendo come input il fatto che vuole tornare al menu
    }

    public void setMessage(String message){
        this.message.setText(message);
        //attenzione al fatto che può essersi disconnesso
    }

}
