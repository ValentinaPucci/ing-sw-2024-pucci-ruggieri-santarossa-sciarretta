package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Controller for the game over screen.
 */
public class GameOverController extends GuiInputReaderController {

    @FXML private Label player0;
    @FXML private Label player1;
    @FXML private Label player2;
    @FXML private Label player3;

    /**
     * List of labels for the players.
     */
    private List<Label> playerLabels;

    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        playerLabels = List.of(player0, player1, player2, player3);
        playerLabels.forEach(label -> label.setVisible(false));
    }

    /**
     * Shows the game over screen.
     * @param model the model to show
     */
    public void show(ModelView model) {
        List<Player> players = model.getClassification();

        IntStream.range(0, Math.min(players.size(), playerLabels.size()))
                .forEach(i -> {
                    Label label = playerLabels.get(i);
                    Player player = players.get(i);
                    label.setText(player.getNickname() + ": " + player.getFinalScore() + " points");
                    label.setVisible(true);
                });
    }
}

