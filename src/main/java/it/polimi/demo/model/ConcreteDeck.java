package it.polimi.demo.model;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;

import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

/**
 * ConcreteDeck class is a class that represents a concrete deck of cards.
 */

public class ConcreteDeck implements Serializable {


    /**
     * The type of the deck.
     */
    public String type;
    /**
     * The concrete cards bunch.
     */
    private CardsCollection concrete_cards_bunch;
    /**
     * The deck factory.
     */
    private DeckFactory deck_factory;
    /**
     * The deck, a stack of Cards.
     */
    private Stack<Card> deck;

    /**
     * Constructor for the ConcreteDeck class.
     * @param type the type of the deck.
     */

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


    /**
     * Push a card into the deck.
     * @param card the card to push.
     */
    public void push(Card card) {
        this.deck.push(card);
    }

    /**
     * Pop a card from the deck.
     * @return the card popped.
     */
    public Card pop() {
        return this.deck.pop();
    }

    /**
     * Pop a ResourceCard from the deck.
     * @return the ResourceCard popped.
     */
    public ResourceCard popResourceCard() {
        return (ResourceCard) this.deck.pop();
    }

    /**
     * Select the first ResourceCard from the deck.
     * @return the first ResourceCard from the deck.
     */
    public ResourceCard selectFirstResourceCard() {
        return (ResourceCard) this.deck.peek();
    }

    /**
     * Pop a GoldCard from the deck.
     * @return the GoldCard popped.
     */
    public GoldCard popGoldCard() {
        return (GoldCard) this.deck.pop();
    }

    /**
     * Select the first GoldCard from the deck.
     * @return the first GoldCard from the deck.
     */
    public GoldCard selectFirstGoldCard() {
        return (GoldCard) this.deck.peek();
    }


    /**
     * Shuffle the deck.
     */
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    /**
     * Check if the deck is empty.
     * @return true if the deck is empty, false otherwise.
     */
    public boolean isEmpty(){
        return deck.isEmpty();
    }

    /**
     * Get the size of the deck.
     * @return the size of the deck.
     */
    public int size(){return deck.size();}

}

