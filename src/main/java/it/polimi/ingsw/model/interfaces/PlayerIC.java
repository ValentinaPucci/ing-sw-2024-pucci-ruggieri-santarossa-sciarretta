package it.polimi.ingsw.model.interfaces;
import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.gameCards.ResourceCard;
import it.polimi.ingsw.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.ingsw.model.*;

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
     * This method check if the player is ready to start
     * @return true if the player is ready to start
     */
    boolean getReadyToStart();

    /**
     * This method check if an object p is equals to the player
     * @param p object to check
     * @return true if the object p is equals to the player
     */
    boolean equals(Object p);

    /**
     * This method check if the player is connected
     * @return true if the player is connected
     */
    boolean isConnected();

    /**
     * This method is used to get the player's id
     * @return id
     */
     int getId();

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
     * This method is used to get the ObjectiveCard that the player chose
     * @return chosen_objective
     */
    ObjectiveCard getChosenObjectiveCard();

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

    /**
     * This method is used to remove a card to those held by the player.
     * This card is the one that will be placed on personal board
     * @param card;
     */
    void removeFromHand(Card card);


    /**
     *
     * @param obj
     */
    void addListener(GameListener obj);

    /**
     *
     * @return
     */
    List<GameListener> getListeners();
}