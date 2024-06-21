package it.polimi.demo.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ErrorController extends SceneController {

    @FXML
    private Button button;

    @FXML
    private TextArea message;

    private boolean needToExitApp;

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void GoToMenu(MouseEvent e){
        if(!needToExitApp) {
            getInputReaderGUI().add("a");
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
