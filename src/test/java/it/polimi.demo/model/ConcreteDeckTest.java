package it.polimi.demo.model;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.GoldCard;
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
    public void testConstructorAndInitialization() {
        // Verify the deck is initialized correctly
        assertNotNull(concreteDeck);
        assertEquals("Resource", concreteDeck.type);
        assertFalse(concreteDeck.isEmpty());
    }

    @Test
    public void testPushAndPop() {
        ResourceCard card = new ResourceCard(13, Orientation.FRONT, Color.RED);
        concreteDeck.push(card);
        assertEquals(card, concreteDeck.pop());
    }

    @Test
    public void testPopResourceCard() {
        ResourceCard resourceCard = new ResourceCard(13, Orientation.FRONT, Color.RED);
        concreteDeck.push(resourceCard);
        assertEquals(resourceCard, concreteDeck.popResourceCard());
    }

    @Test
    public void testSelectFirstResourceCard() {
        ResourceCard resourceCard = new ResourceCard(13, Orientation.FRONT, Color.RED);
        concreteDeck.push(resourceCard);
        assertEquals(resourceCard, concreteDeck.selectFirstResourceCard());
    }

    @Test
    public void testPopGoldCard() {
        GoldCard goldCard = new GoldCard(14, Orientation.BACK, Color.GREEN);
        concreteDeck.push(goldCard);
        assertEquals(goldCard, concreteDeck.popGoldCard());
    }

    @Test
    public void testSelectFirstGoldCard() {
        GoldCard goldCard = new GoldCard(14, Orientation.BACK, Color.GREEN);
        concreteDeck.push(goldCard);
        assertEquals(goldCard, concreteDeck.selectFirstGoldCard());
    }

    @Test
    public void testShuffle() {
        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.RED);
        ResourceCard card2 = new ResourceCard(2, Orientation.BACK, Color.GREEN);
        concreteDeck.push(card1);
        concreteDeck.push(card2);
        concreteDeck.shuffle();
        // Verify the deck is shuffled by checking the order is not predictable
       // assertNotEquals(card1, concreteDeck.pop());
        assertNotEquals(card2, concreteDeck.pop());
    }

    @Test
    public void testIsEmpty() {
        // Deck is not empty initially due to initial population in the constructor
        assertFalse(concreteDeck.isEmpty());
        while (!concreteDeck.isEmpty()) {
            concreteDeck.pop();
        }
        assertTrue(concreteDeck.isEmpty());
    }

    @Test
    public void testSize() {
        int initialSize = concreteDeck.size();
        ResourceCard card = new ResourceCard(13, Orientation.FRONT, Color.RED);
        concreteDeck.push(card);
        assertEquals(initialSize + 1, concreteDeck.size());
        concreteDeck.pop();
        assertEquals(initialSize, concreteDeck.size());
    }
}
