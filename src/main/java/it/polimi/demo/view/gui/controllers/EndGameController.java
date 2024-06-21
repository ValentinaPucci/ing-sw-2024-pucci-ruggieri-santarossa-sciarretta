package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndGameController extends GuiInputReaderController {


    @FXML private Label player0;
    @FXML private Label player1;
    @FXML private Label player2;
    @FXML private Label player3;

     public void show(ModelView model) {
        player0.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);

        int i=0;
        Label tmp = null;

        for (Player p:model.getClassification()){
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
}
