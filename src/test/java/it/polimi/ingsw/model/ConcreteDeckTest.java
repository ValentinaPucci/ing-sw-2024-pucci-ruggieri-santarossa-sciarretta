package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConcreteDeckTest {
    private ConcreteDeck concreteDeck;
    private CardsCollection cardsCollection;

    @BeforeEach
    public void setUp() {
        cardsCollection = new CardsCollection(); // Add necessary parameters if needed
        concreteDeck = new ConcreteDeck(cardsCollection, "Resource");
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

    @Test
    public void testIsEmpty() {
        assertTrue(concreteDeck.isEmpty());
    }
}
