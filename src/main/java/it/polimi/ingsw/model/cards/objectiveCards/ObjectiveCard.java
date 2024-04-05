package it.polimi.ingsw.model.cards.objectiveCards;

import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enumerations.Orientation;

public abstract class ObjectiveCard extends Card {
    private int points;

    /**
     * Costruttore protetto per evitare istanziazione diretta
     * @param id l'id dell'obiettivo
     * @param orientation front o back
     * @param points il punteggio dell'obiettivo
     */
    protected ObjectiveCard(int id, Orientation orientation, int points) {
        super(id, orientation);
        this.points = points;
    }

    /**
     * Metodo per ottenere il punteggio dell'obiettivo
     * @return il punteggio dell'obiettivo
     */
    public int getPoints() {
        return points;
    }

    /**
     * Metodo astratto per calcolare il punteggio dell'obiettivo in base allo schema personale del giocatore
     * @param personal_board lo schema personale del giocatore
     * @return il punteggio calcolato dell'obiettivo
     */
    public abstract int calculateScore(PersonalBoard personal_board);
}
