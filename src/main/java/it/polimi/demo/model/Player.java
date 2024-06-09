package it.polimi.demo.model;

import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    private String nickname;
    private List<ResourceCard> card_hand;
    private PersonalBoard personal_board;
    private List<ObjectiveCard> secret_objectives;

    private List<StarterCard> starter_card_to_chose;
    private ObjectiveCard chosen_objective;
    private StarterCard starter_card;
    private ResourceCard chosen_card;
    private int score_board_position;
    private int final_score;
    private boolean is_connected;
    private boolean is_ready_to_start;

    //private transient List<Listener> listeners;

    public Player(String nickname) {
        this.nickname = nickname;
        this.card_hand = new ArrayList<>();
        this.personal_board = new PersonalBoard();
        this.secret_objectives = new ArrayList<>();
        this.starter_card_to_chose = new ArrayList<>();
        this.chosen_objective = null;
        this.starter_card = null;
        this.chosen_card = null;
        this.score_board_position = 0;
        this.final_score = 0;
        //this.listeners = new ArrayList<>();
        this.is_connected = false;
        this.is_ready_to_start = false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    
    public PersonalBoard getPersonalBoard() {
        return personal_board;
    }

    public void setPersonalBoard(PersonalBoard personal_board) {
        this.personal_board = personal_board;
    }

    
    public ResourceCard getChosenGameCard() {
        return chosen_card;
    }

    public void setChosenGameCard(ResourceCard chosen_card) {
        this.chosen_card = chosen_card;
    }

    public void setChosenGameCard(GoldCard chosen_card) {
        this.chosen_card = chosen_card;
    }

    public void setChosenObjectiveCard(ObjectiveCard chosen_objective) {
        this.chosen_objective = chosen_objective;
    }

    
    public StarterCard getStarterCard() {
        return starter_card;
    }

    public void setStarterCard(StarterCard starter_card) {
        this.starter_card = starter_card;
    }

    
    public ObjectiveCard getChosenObjectiveCard() {
        return chosen_objective;
    }

    public List<ObjectiveCard> getSecretObjectiveCards() {
        return secret_objectives ;
    }


    /**
     * set the secret objective at the start of the game. Then, the player
     * decides which one to keep (only one secret objective is admissible)
     * @param objective1 objective 1
     * @param objective2 objective 2
     */
    
    public void setSecretObjectives(ObjectiveCard objective1, ObjectiveCard objective2) {
        secret_objectives.add(objective1);
        secret_objectives.add(objective2);
    }

    public void setStarterCardToChose(StarterCard starterCard1, StarterCard starterCard2) {
        starter_card_to_chose.add(starterCard1);
        starter_card_to_chose.add(starterCard2);
    }

    public List<StarterCard> getStarterCardToChose() { return this.starter_card_to_chose;}

    
    public int getScoreBoardPosition() {
        return score_board_position;
    }

    public void setScoreBoardPosition(int score_board_position) {
        this.score_board_position = score_board_position;
    }

    
    public int getFinalScore() {
        return final_score;
    }

    public void setFinalScore(int final_score) {
        this.final_score = final_score;
    }

    
    public void addToHand(ResourceCard card) {
        card_hand.add(card);
    }

    public List<ResourceCard> getCardHand() { return card_hand; }

    
    public ArrayList<Integer> getCardHandIds(){
       ArrayList<Integer> cardHandIds = new ArrayList<>();
        for (ResourceCard resourceCard : card_hand) {
            cardHandIds.add(resourceCard.getId());
        }
       return cardHandIds;
    }

    
    public void removeFromHand(ResourceCard card) {
        card_hand.remove(card);
    }

    
    public void removeFromHand(GoldCard card) {card_hand.remove(card);}

    public List<ResourceCard> getHand() {
        return card_hand;
    }

    
    public List<ResourceCard> getHandIC() {
        return new ArrayList<>(card_hand);
    }

    
    public boolean getReadyToStart() {
        return is_ready_to_start;
    }

    public void setAsReadyToStart() {
        this.is_ready_to_start = true;
    }

    public void setAsNotReadyToStart() {
        this.is_ready_to_start = false;
    }

    
    public boolean getIsConnected() {
        return is_connected;
    }

    public void setAsConnected() {
        this.is_connected = true;
    }

    public void setAsNotConnected() {
        this.is_connected = false;
    }

    
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
        personal_board.bruteForcePlaceCardSE(starter_card, 250, 250);
    }

    /**
     *
     * @return the current points of the player according to his/her personal board
     */
    public int getCurrentPoints() {
        return personal_board.getPoints();
    }

    
    public ResourceCard getLastChosenCard() {
        return chosen_card;
    }

    
    public boolean isLast() {
        //TODO: IMPLEMENT!
        return false;
    }

    
    public Integer[] getSecretObjectiveCardsIds() {
        Integer[] personalObjectiveIds = new Integer[2];
        personalObjectiveIds[0] = getSecretObjectiveCards().getFirst().getId();
        personalObjectiveIds[1] = getSecretObjectiveCards().get(1).getId();
        return personalObjectiveIds;
    }

    public int scoreOnlyObjectiveCards() {
        return getFinalScore() - getScoreBoardPosition();
    }
}

