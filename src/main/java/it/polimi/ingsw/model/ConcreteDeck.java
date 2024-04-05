package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Card;

import java.util.Collections;
import java.util.Stack;

public class ConcreteDeck {
    private String type;
    private CardsCollection concrete_cards_bunch;
    private DeckFactory deck_factory;
    private Stack<Card> deck;


    // To Use this class you call the constructor with the path to the json and the "type" of cards you want in the deck.
    // Then you can use on it methods such as shuffle or pop.


    // This one is only for Resources cards: type is either "Resource" or "Gold".
    public ConcreteDeck(CardsCollection concrete_cards_bunch, String type){
        // Path to the .json file
        this.deck_factory = new DeckFactory();
        this.concrete_cards_bunch = concrete_cards_bunch;
        this.type = type;
        this.deck = new Stack<>();
        this.concrete_cards_bunch = this.deck_factory.createDeck(this.type);
        this.deck.addAll(this.concrete_cards_bunch.cards);
    }

    // Second constructor to use for ayother type of card besides Resources cards that use the first one.


    public void push(Card card) {
        this.deck.push(card);
    }

    public Card pop() {
        return this.deck.pop();
        }


    // TODO: Implement this method in other classes.
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }

}

