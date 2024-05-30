package it.polimi.demo.model.interfaces;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This interface is implemented by Player
 */
public interface PlayerIC {
    /**
     * This method is used to get the nickname of the player
     * @return the nickname of the player
     */
    String getNickname();

    /**
     * This method is used to get the player's personal_board
     * @return player's personal_board
     */
    PersonalBoard getPersonalBoard();

    /**
     *  This method is used to get the card that the player wants to place
     * @return chosen_card
     */
    ResourceCard getChosenGameCard();

    /**
     *  This method is used to get the card that the player wants to place
     * @return chosen_card
     */
    StarterCard getStarterCard();

    List<StarterCard> getStarterCardToChose();

    /**
     * This method is used to get the ObjectiveCard that the player chose
     * @return chosen_objective
     */
    ObjectiveCard getChosenObjectiveCard();

    List<ObjectiveCard> getSecretObjectiveCards();

    /**
     * set the secret objective at the start of the game. Then, the player
     * decides which one to keep (only one secret objective is admissible)
     */
    void setSecretObjectives(ObjectiveCard objective1, ObjectiveCard objective2);

    /**
     * This method is used to get the player's position on common board
     * @return score_board_position
     */
    int getScoreBoardPosition();

    /**
     * This method is used to get the player's score at the end of the game
     * @return final_score
     */
    int getFinalScore();

    /**
     * This method is used to add a card to those held by the player
     * @param card;
     */
    void addToHand(Card card);

    ArrayList<Integer> getCardHandIds();

    /**
     * This method is used to remove a card to those held by the player.
     * This card is the one that will be placed on personal board
     * @param card;
     */
    void removeFromHand(Card card);

    /**
     *
     * @return the player's hand
     */
    List<ResourceCardIC> getHandIC();

    List<ResourceCard> getHand();

    /**
     * This method check if the player is ready to start
     * @return true if the player is ready to start
     */
    boolean getReadyToStart();

    /**
     * This method check if the player is connected
     * @return true if the player is connected
     */
    boolean getIsConnected();

    /**
     * This method check if an object p is equals to the player
     * @param p object to check
     * @return true if the object p is equals to the player
     */
    boolean equals(Object p);

    ResourceCard getLastChosenCard();

    boolean isLast();

    Integer[] getSecretObjectiveCardsIds();
}