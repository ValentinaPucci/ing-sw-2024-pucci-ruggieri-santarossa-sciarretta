package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String nickname;
    private List<Card> card_hand;
    private PersonalBoard personal_board; // correspondence between PersonalBoard and Player
    private ObjectiveCard[] secret_objectives;
    private ObjectiveCard chosen_objective;
    private StarterCard starter_card;
    private ResourceCard chosen_card;

   public Player(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.card_hand = new ArrayList<>();
        this.personal_board = new PersonalBoard();
        this.secret_objectives[0] = null;
        this.secret_objectives[1] = null;
        this.chosen_objective = null;
        this.starter_card = null;
    }

    public void setObjectiveCard(ObjectiveCard chosen_objective) {//TODO: deck o no?
        this.chosen_objective = chosen_objective;
    }
    public String getNickname() {//TODO: deck o no?
        return this.nickname;
    }




    public void playStarterCard() {
       this.personal_board.bruteForcePlaceStarterCard(starter_card);
    }
    public int getId(){return id;}

    public PersonalBoard getPersonalBoard() {
        return personal_board;
    }

    public void setChosenGameCard(ResourceCard chosen_card) {
        // the choice arrives from controller, the client choose the card he wants to play
        //the card is saved and then it will be played using the method getChosenGameCard
        this.chosen_card = chosen_card;
    }

    public ResourceCard getChosenGameCard() {
        return this.chosen_card;
    }

    public void addToHand(Card card){
       this.card_hand.add(card);
    }


    public void setSecretObjectives(ObjectiveCard objective1, ObjectiveCard objective2){
        secret_objectives[0] = objective1;
        secret_objectives[1] = objective2;
    }
}

