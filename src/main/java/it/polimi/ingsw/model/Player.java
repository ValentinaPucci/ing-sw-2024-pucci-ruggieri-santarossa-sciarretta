package it.polimi.ingsw.model;

import it.polimi.ingsw.interfaces.PlayerIC;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.ingsw.model.cards.gameCards.ResourceCard;
import it.polimi.ingsw.model.cards.gameCards.StarterCard;
import it.polimi.ingsw.listener.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Player implements Serializable, PlayerIC {
    private int id;
    private String nickname;
    private List<Card> card_hand;
    private PersonalBoard personal_board;
    private ObjectiveCard[] secret_objectives;
    private ObjectiveCard chosen_objective;
    private StarterCard starter_card;
    private ResourceCard chosen_card;
    private int score_board_position;
    private int final_score;
    private GameModel game;
    private boolean connected = true;
    private boolean ready_to_start = false;
    private List<GameListener> listeners;

    public Player(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.card_hand = new ArrayList<>();
        this.personal_board = new PersonalBoard();
        this.secret_objectives = new ArrayList<ObjectiveCard>().toArray(new ObjectiveCard[0]);
        this.chosen_objective = null;
        this.starter_card = null;
        this.score_board_position = 0;
        this.final_score = 0;
    }

    public void setObjectiveCard(ObjectiveCard chosen_objective) {
        // TODO: la scelta arriva dal controller
        this.chosen_objective = chosen_objective;
    }

    public void playStarterCard() {
        personal_board.bruteForcePlaceCardSE(starter_card, 50, 50);
    }

    public int getId() {
        return id;
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

    public void setChosenGameCard(ResourceCard chosen_card) { //choose the card you want to play from your hand
        // TODO: la scelta arriva dal controller
        this.chosen_card = chosen_card;
    }

    public ResourceCard getChosenGameCard() {
        removeFromHand(chosen_card);
        return this.chosen_card;
    }

    /**
     * @return chosen_objective
     */
    public ObjectiveCard getChosenObjectiveCard() {
        return this.chosen_objective;
    }


    public void setScoreBoardPosition() {
        this.score_board_position = game.getCommonBoard().getPlayerPosition(id);
    }

    public int getScoreBoardPosition() {
        return this.score_board_position;
    }

    public int getFinalScore() {
        return this.final_score;
    }

    //TODO: da rivedere il collegamento con game
    public void setFinalScore() {
        this.final_score = game.getFinalScore(id);
    }

    public void addToHand(Card card) {
        this.card_hand.add(card);
    }

    public void removeFromHand(Card card) {
        this.card_hand.remove(card);
    }


    public void setSecretObjectives(ObjectiveCard objective1, ObjectiveCard objective2) {
        secret_objectives[0] = objective1;
        secret_objectives[1] = objective2;
    }

    /**
     * @return the player's connection status
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @param connected sets the player's connection status to the param
     */
    public void setConnection(boolean connected) {
        this.connected = connected;
    }

    /**
     * @return the player's readiness to start
     */
    public boolean getReadyToStart() {
        return ready_to_start;
    }

    /**
     * Sets the player as ready to play
     */
    public void setReadyToStart() {
        ready_to_start = true;
    }

    /**
     * Sets the player as not ready to play
     */
    public void setNotReadyToStart() {
        ready_to_start = false;
    }

    public void addListener(GameListener obj) {
        listeners.add(obj);
    }

    /**
     * @return the list of gameListeners attached to the player
     */
    public List<GameListener> getListeners() {
        return listeners;
    }

//    public void addPoints(Card card) {
//        personal_board.updatePoints(card);
//        notify_addedPoint();
//
//    }
//    private void notify_addedPoint(int point, Game model) {
//        Iterator<GameListener> i = listeners.iterator();
//        while (i.hasNext()) {
//            GameListener l = i.next();
//            try {
//                presonal_board.update
//            } catch (RemoteException e) {
//                printAsync("During notification of notify_addedPoint, a disconnection has been detected before heartbeat");
//                i.remove();
//            }
//        }
//    }
// }
}

