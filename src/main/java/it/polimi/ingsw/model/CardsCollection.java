package src.main.java.it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CardsCollection {
    public List<Card> cards;

    public CardsCollection() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

    /**
     * To do: populateDeck read a .json file and populate the data structure using
     * the constructur and the setter of Card subclasses.
     */
    public void populateDeck() {}
}
