package src.main.java.it.polimi.ingsw.model;

import java.util.Collections;
import java.util.Stack;

public class ConcreteDeck {

    private CardsCollection concrete_cards_bunch;
    private Stack<Card>  deck;

    public ConcreteDeck(CardsCollection cards_bunch) {
        deck = new Stack<>();
        for (Card c : cards_bunch.cards)
            this.concrete_cards_bunch.addCard(c);
    }

    public void shuffle() {
        Collections.shuffle(concrete_cards_bunch.cards);
    }

    /**
     * fill the deck (a stack of cards) with the cards contained
     * inside a bunch of cards.
     */
    public void deckFiller() {
        for (Card c : concrete_cards_bunch.cards) {
            deck.push(c);
        }
    }


}
