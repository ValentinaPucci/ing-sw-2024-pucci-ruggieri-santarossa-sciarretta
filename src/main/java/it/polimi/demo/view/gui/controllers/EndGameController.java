package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Controller class for the end game scene
 */
public class EndGameController extends GuiInputReaderController {

    @FXML private Label player0;
    @FXML private Label player1;
    @FXML private Label player2;
    @FXML private Label player3;

    /**
     * Method that shows the final classification of the players (leaderboard)
     * @param model the model to show
     */
    public void show(ModelView model) {
        List<Label> players = List.of(player0, player1, player2, player3);

        players.forEach(player -> player.setVisible(false));

        IntStream.range(0, model.getClassification().size())
                .forEach(i -> {
                    Player p = model.getClassification().get(i);
                    Label tmp = players.get(i);
                    tmp.setText(p.getNickname() + ": " + p.getFinalScore() + " points");
                    tmp.setVisible(true);
                });
    }

}
