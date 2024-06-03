package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ErrorController extends GenericController {

    @FXML
    private Button button;

    @FXML
    private TextArea message;

    private boolean needToExitApp;

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void GoToMenu(ActionEvent e){
        if(!needToExitApp) {
            getInputReaderGUI().addTxt("a");
        }else{
            System.exit(-1);
        }
    }

    public void setMessage(String msg, boolean needToExitApp){
        this.message.setText(msg);
        if(needToExitApp){
            button.setText("Close App");
        }
        this.needToExitApp=needToExitApp;

    }

}
