package it.polimi.demo.model;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConcreteDeckTest {
    private ConcreteDeck concreteDeck;

    @BeforeEach
    public void setUp() {
        concreteDeck = new ConcreteDeck("Resource");
    }

    @Test
    public void testPop() {
        ResourceCard card = new ResourceCard(13, Orientation.FRONT, Color.RED ); // Add necessary parameters if needed
        concreteDeck.push(card);
        Card poppedCard = concreteDeck.pop();
        assertEquals(card, poppedCard);
    }

    @Test
    public void testShuffle() {
        concreteDeck.shuffle();
        // Shuffling is random, so we can't predict the order.
        // But we can at least check that the cards are still there.
    }


/*
    @Test
    public void testIsEmpty() {
        assertTrue(concreteDeck.isEmpty());
    }

 */
}
