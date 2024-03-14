package src.main.java.it.polimi.ingsw.model;

import src.main.java.it.polimi.ingsw.model.ObjectiveCard;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String username;
    private List<Card> card_hand; // Assuming Card is a class defined elsewhere
    private PersonalBoard personal_board; // Assuming PersonalBoard is a class defined elsewhere
    private List<ObjectiveCard> secret_objectives; // Assuming ObjectiveCard is a class defined elsewhere
    private ObjectiveCard chosen_objective;

    private StarterCard starter_card; // Assuming StarterCard is a class defined elsewhere
    private boolean winner;
    private int score_board_position;
    private int final_score;
   public Player(int id, String username) {
        this.id = id;
        this.username = username;
        this.card_hand = new ArrayList<>();
        this.personal_board = new PersonalBoard();
        this.secret_objectives = new ArrayList<>();
        this.chosen_objective = null;
        this.starter_card = null;
        this.winner = false;
        this.score_board_position = 0;
        this.final_score = 0;
    }

    public void chooseObjectiveCard() {

    }

    public void playBeginningCard() {
        // Implementation
    }

    public void playGameCard() {
        // Implementation
    }

    public void chooseGameCard() {
        // Implementation
    }

    public void takeTurn() {
        // Implementation
    }

    // Additional getter and setter methods as needed
}

