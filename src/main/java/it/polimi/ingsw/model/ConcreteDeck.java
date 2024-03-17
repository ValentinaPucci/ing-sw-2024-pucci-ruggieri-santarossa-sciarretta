package src.main.java.it.polimi.ingsw.model;

import java.util.Collections;
import java.util.Stack;

public class ConcreteDeck {
    private String type;
    private CardsCollection concrete_cards_bunch;
    private Stack<Card>  deck;

   // To Use this class you call the constructor with the path to the json and the "type" of cards you want in the deck.
    // Then you can use on it methods such as shuffle or pop.

    public ConcreteDeck(CardsCollection concrete_cards_bunch, String path, String type){
        // Path to the .json file
        this.concrete_cards_bunch = concrete_cards_bunch;
        this.type = type;
        this.deck = new Stack<>();
        this.concrete_cards_bunch.populateDeck(path, this.type);
        this.deck.addAll(this.concrete_cards_bunch.cards);
    }

    public void push(Card card) {
        this.deck.push(card);
    }

    public Card pop() {
        return this.deck.pop();
        }

    public void shuffle() {
        Collections.shuffle(this.deck);
    }

//
//    /**
//     * fill the deck (a stack of cards) with the cards contained
//     * inside a bunch of cards.
//     */
//    public void deckFiller() {
//        for (Card c : concrete_cards_bunch.cards) {
//            deck.push(c);
//        }
//    }
//
//    public String getDeckType(){
//        return this.type;
//    }

}

