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
     *
     * @return
     */
     int getId();

    /**
     *
     * @return
     */
    PersonalBoard getPersonalBoard();

    /**
     *
     * @return
     */
    ResourceCard getChosenGameCard();

    /**
     *
     * @return
     */
    ObjectiveCard getChosenObjectiveCard();

    /**
     *
     * @return
     */
    int getScoreBoardPosition();

    /**
     *
     * @return
     */
    int getFinalScore();

    /**
     *
     * @param card
     */
    void addToHand(Card card);

    /**
     *
     * @param card
     */
    void removeFromHand(Card card);

    /**
     *
     * @param objective1
     * @param objective2
     */
    void setSecretObjectives(ObjectiveCard objective1, ObjectiveCard objective2);

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