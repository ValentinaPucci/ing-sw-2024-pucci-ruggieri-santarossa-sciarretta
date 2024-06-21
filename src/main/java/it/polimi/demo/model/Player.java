package it.polimi.demo.model;

import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 * This class provides getters and setters to access various aspects of the player state.
 */
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
    private boolean is_ready_to_start;

    /**
     * Constructor for the Player class.
     * @param nickname the nickname of the player
     */
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
        this.is_ready_to_start = false;
    }

    /**
     * getter for the nickname of the player
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * setter for the nickname of the player
     * @param nickname the new nickname of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * getter for the personal board of the player
     * @return the personal board of the player
     */
    public PersonalBoard getPersonalBoard() {
        return personal_board;
    }

    /**
     * getter for the chosen game card
     * @return the chosen game card
     */
    public ResourceCard getChosenGameCard() {
        return chosen_card;
    }

    /**
     * setter for the chosen game card (resource)
     * @param chosen_card the chosen game card
     */
    public void setChosenGameCard(ResourceCard chosen_card) {
        this.chosen_card = chosen_card;
    }

    /**
     * setter for the chosen game card (gold)
     * @param chosen_card the chosen game card
     */
    public void setChosenGameCard(GoldCard chosen_card) {
        this.chosen_card = chosen_card;
    }

    /**
     * getter for the starter card of the player
     * @return the starter card of the player
     */
    public StarterCard getStarterCard() {
        return starter_card;
    }

    /**
     * setter for the starter card of the player
     * @param starter_card the starter card of the player
     */
    public void setStarterCard(StarterCard starter_card) {
        this.starter_card = starter_card;
    }

    /**
     * setter for the chosen objective card
     * @param chosen_objective the chosen objective card
     */
    public void setChosenObjectiveCard(ObjectiveCard chosen_objective) {
        this.chosen_objective = chosen_objective;
    }

    /**
     * getter for the chosen objective card
     * @return the chosen objective card
     */
    public ObjectiveCard getChosenObjectiveCard() {
        return chosen_objective;
    }

    /**
     * getter for the secret objective cards of the player
     * @return the secret objective cards of the player
     */
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

    /**
     * set the starter card to chose at the start of the game
     * @param starterCard1 starter card 1
     * @param starterCard2 starter card 2
     */
    public void setStarterCardToChose(StarterCard starterCard1, StarterCard starterCard2) {
        starter_card_to_chose.add(starterCard1);
        starter_card_to_chose.add(starterCard2);
    }

    /**
     * getter for the starter card to chose at the start of the game
     * @return the starter card to chose at the start of the game
     */
    public List<StarterCard> getStarterCardToChose() { return this.starter_card_to_chose;}

    /**
     * set the position of the player in the score board
     * @return the position of the player in the score board
     */
    public int getScoreBoardPosition() {
        return score_board_position;
    }

    /**
     * get the final score of the player
     * @return the final score of the player
     */
    public int getFinalScore() {
        return final_score;
    }

    /**
     * set the final score of the player
     * @param final_score the final score of the player
     */
    public void setFinalScore(int final_score) {
        this.final_score = final_score;
    }

    /**
     * add a card to the hand of the player
     * @param card the card to add to the hand of the player
     */
    public void addToHand(ResourceCard card) {
        card_hand.add(card);
    }

    /**
     * getter for the hand-ids of the player
     * @return the hand-ids of the player
     */
    public ArrayList<Integer> getCardHandIds(){
       ArrayList<Integer> cardHandIds = new ArrayList<>();
        for (ResourceCard resourceCard : card_hand) {
            cardHandIds.add(resourceCard.getId());
        }
       return cardHandIds;
    }

    /**
     * getter for the hand of the player
     * @return the hand of the player
     */
    public List<ResourceCard> getHand() {
        return card_hand;
    }

    /**
     * @return true if the player is ready to start the game, false otherwise
     */
    public boolean getReadyToStart() {
        return is_ready_to_start;
    }

    /**
     * set the player as ready to start the game
     */
    public void setAsReadyToStart() {
        this.is_ready_to_start = true;
    }

    /**
     * @param o the object to compare
     * @return true if the object is a player with the same nickname, false otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Player) {
            Player p = (Player) o;
            return p.getNickname().equals(nickname);
        }
        else
            return false;
    }

    /**
     *
     * @return the current points of the player according to his/her personal board
     */
    public int getCurrentPoints() {
        return personal_board.getPoints();
    }

    /**
     * getter for the secret objective cards ids of the player
     * @return the secret objective cards ids of the player
     */
    public Integer[] getSecretObjectiveCardsIds() {
        Integer[] personalObjectiveIds = new Integer[2];
        personalObjectiveIds[0] = getSecretObjectiveCards().getFirst().getId();
        personalObjectiveIds[1] = getSecretObjectiveCards().get(1).getId();
        return personalObjectiveIds;
    }

    /**
     * getter for the clean score of a player,
     * i.e. the score without considering the position in the score board
     * @return the clean score of a player
     */
    public int scoreOnlyObjectiveCards() {
        return getFinalScore() - getScoreBoardPosition();
    }
}

