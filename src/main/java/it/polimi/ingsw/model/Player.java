package src.main.java.it.polimi.ingsw.model;

import src.main.java.it.polimi.ingsw.model.ObjectiveCard;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String username;
    private List<Card> card_hand;
    private PersonalBoard personal_board; // correspondence between PersonalBoard and Player
    private List<ObjectiveCard> secret_objectives; //
    private ObjectiveCard chosen_objective;
    private StarterCard starter_card;
    private boolean partial_winner;
    private boolean final_winner;
    private ResourceCard chosen_card;
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
        this.partial_winner = false;
        this.final_winner = false;
        this.score_board_position = 0;
        this.final_score = 0;
    }

    public void setObjectiveCard(ObjectiveCard chosen_objective) {//TODO: deck o no?
        this.chosen_objective = chosen_objective;
    }

    public void playStarterCard() {
       //call brute force starter card from personal board
    }

    public void setChosenGameCard(ResourceCard chosen_card) { //choose the card you want to play from your hand
        // la scelta arriva dal controller
        this.chosen_card = chosen_card;
    }

    public void addToHand(Card card){
       this.card_hand.add(card);
    }
    public void setPartialWinner(boolean partial_winner) { //choose the card you want to play from your hand
        // la scelta arriva dalla logica
        this.partial_winner = partial_winner;
    }

    public void setFinalWinner(boolean final_winner) { //choose the card you want to play from your hand
        this.final_winner = final_winner;
    }
}

