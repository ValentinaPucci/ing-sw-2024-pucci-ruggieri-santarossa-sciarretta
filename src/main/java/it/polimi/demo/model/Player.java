package it.polimi.demo.model;

import it.polimi.demo.model.interfaces.PlayerIC;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.listener.*;
import it.polimi.demo.model.interfaces.ResourceCardIC;

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerIC {

    private String nickname;
    private List<ResourceCard> card_hand;
    private PersonalBoard personal_board;
    private ObjectiveCard[] secret_objectives;

    private StarterCard[] starter_card_to_chose;
    private ObjectiveCard chosen_objective;
    private StarterCard starter_card;
    private ResourceCard chosen_card;
    private int score_board_position;
    private int final_score;
    private List<GameListener> listeners;
    private boolean is_connected;
    private boolean is_ready_to_start;

    public Player(String nickname) {
        this.nickname = nickname;
        this.card_hand = new ArrayList<>();
        this.personal_board = new PersonalBoard();
        this.secret_objectives = new ObjectiveCard[2];
        this.starter_card_to_chose = new StarterCard[2];
        this.chosen_objective = null;
        this.starter_card = null;
        this.chosen_card = null;
        this.score_board_position = 0;
        this.final_score = 0;
        this.listeners = new ArrayList<>();
        this.is_connected = false;
        this.is_ready_to_start = false;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public PersonalBoard getPersonalBoard() {
        return personal_board;
    }

    public void setPersonalBoard(PersonalBoard personal_board) {
        this.personal_board = personal_board;
    }

    @Override
    public ResourceCard getChosenGameCard() {
        return chosen_card;
    }

    public void setChosenGameCard(ResourceCard chosen_card) {
        this.chosen_card = chosen_card;
    }

    @Override
    public StarterCard getStarterCard() {
        return starter_card;
    }

    public void setStarterCard(StarterCard starter_card) {
        this.starter_card = starter_card;
    }

    @Override
    public ObjectiveCard getChosenObjectiveCard() {
        return chosen_objective;
    }

    public ObjectiveCard[] getSecretObjectiveCards() {
        return secret_objectives ;
    }

    public void setChosenObjectiveCard(ObjectiveCard chosen_objective) {
        this.chosen_objective = chosen_objective;
    }

    /**
     * set the secret objective at the start of the game. Then, the player
     * decides which one to keep (only one secret objective is admissible)
     * @param objective1 objective 1
     * @param objective2 objective 2
     */
    @Override
    public void setSecretObjectives(ObjectiveCard objective1, ObjectiveCard objective2) {
        secret_objectives[0] = objective1;
        secret_objectives[1] = objective2;
    }

    public void setStarterCardToChose(StarterCard starterCard1, StarterCard starterCard2){
        starter_card_to_chose[0] = starterCard1;
        starter_card_to_chose[1] = starterCard2;
    }

    public StarterCard[] getStarterCardToChose(){ return this.starter_card_to_chose;}

    @Override
    public int getScoreBoardPosition() {
        return score_board_position;
    }

    public void setScoreBoardPosition(int score_board_position) {
        this.score_board_position = score_board_position;
    }

    @Override
    public int getFinalScore() {
        return final_score;
    }

    public void setFinalScore(int final_score) {
        this.final_score = final_score;
    }

    public void addToHand(Card card) {
        card_hand.add((ResourceCard) card);
    }

    public List<ResourceCard> getCardHand(){ return card_hand; }

    public void setHand(List<ResourceCard> card_hand) {
        this.card_hand.clear();
        this.card_hand.addAll(card_hand);
    }

    @Override
    public void removeFromHand(Card card) {
        card_hand.remove(card);
    }

    public List<ResourceCard> getHand() {
        return card_hand;
    }

    @Override
    public List<ResourceCardIC> getHandIC() {
        return new ArrayList<>(card_hand);
    }

    @Override
    public void addListener(GameListener obj) {
        listeners.add(obj);
    }

    @Override
    public void removeListener(GameListener obj) {
        listeners.remove(obj);
    }

    @Override
    public List<GameListener> getListeners() {
        return listeners;
    }

    @Override
    public boolean getReadyToStart() {
        return is_ready_to_start;
    }

    public void setAsReadyToStart() {
        this.is_ready_to_start = true;
    }

    public void setAsNotReadyToStart() {
        this.is_ready_to_start = false;
    }

    @Override
    public boolean getIsConnected() {
        return is_connected;
    }

    public void setAsConnected() {
        this.is_connected = true;
    }

    public void setAsNotConnected() {
        this.is_connected = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Player) {
            Player p = (Player) o;
            return p.getNickname().equals(nickname);
        }
        else
            return false;
    }

    /**
     * Place the card in the middle of the personal board
     */
    public void playStarterCard() {
        personal_board.bruteForcePlaceCardSE(starter_card, 500, 500);
    }

    /**
     *
     * @return the current points of the player according to his/her personal board
     */
    public int getCurrentPoints() {
        return personal_board.getPoints();
    }

    @Override
    public ResourceCard getLastChosenCard() {
        return chosen_card;
    }

    @Override
    public boolean isLast() {
        //TODO: IMPLEMENT!
        return false;
    }
}

