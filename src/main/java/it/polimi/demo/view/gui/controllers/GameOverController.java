package it.polimi.demo.view.gui.controllers;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.interfaces.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class GameOverController extends GenericController{

    @FXML
    private Button buttonMenu;

    @FXML
    private Label player0;

    @FXML
    private Label player1;

    @FXML
    private Label player2;

    @FXML
    private Label player3;

     public void show(GameModelImmutable model) {
        player0.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        buttonMenu.setVisible(true);

        int i=0;
        Label tmp = null;

        for(PlayerIC p:model.getClassification()){
            switch (i){
                case 0-> tmp=player0;
                case 1-> tmp=player1;
                case 2-> tmp=player2;
                case 3-> tmp=player3;
            }

            tmp.setText(p.getNickname()+": "+p.getFinalScore()+" points");
            tmp.setVisible(true);
            i++;
        }

    }

    /**
     * Show the button to return to the menu.
     */
    public void showBtnReturnToMenu() {
        buttonMenu.setVisible(true);//not necessary
    }

    @FXML
    void goToMenu(ActionEvent event) {
        getInputReaderGUI().addTxt("a");
    }

}
