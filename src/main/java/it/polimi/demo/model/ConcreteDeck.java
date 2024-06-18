package it.polimi.demo.model;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;

import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

public class ConcreteDeck implements Serializable {

    private String type;
    private CardsCollection concrete_cards_bunch;
    private DeckFactory deck_factory;
    private Stack<Card> deck;

    // To Use this class you call the constructor with the path to the json and the "type" of cards you want in the deck.
    // Then you can use on it methods such as shuffle or pop.

    // This one is only for Resources cards: type is either "Resource" or "Gold".
    public ConcreteDeck(String type){
        // Path to the .json file
        this.deck_factory = new DeckFactory();
        this.type = type;
        this.deck = new Stack<>();
        this.concrete_cards_bunch = this.deck_factory.createDeck(this.type);
        this.deck.addAll(this.concrete_cards_bunch.cards);
    }

    // Second constructor to use for another type of card besides Resources cards that use the first one.

    public void push(Card card) {
        this.deck.push(card);
    }

    public Card pop() {
        return this.deck.pop();
    }

    public ResourceCard popResourceCard() {
        return (ResourceCard) this.deck.pop();
    }

    public ResourceCard selectFirstResourceCard() {
        return (ResourceCard) this.deck.peek();
    }

    public GoldCard popGoldCard() {
        return (GoldCard) this.deck.pop();
    }

    public GoldCard selectFirstGoldCard() {
        return (GoldCard) this.deck.peek();
    }

    // TODO: Implement this method in other classes.
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }

    public int size(){return deck.size();}

}

