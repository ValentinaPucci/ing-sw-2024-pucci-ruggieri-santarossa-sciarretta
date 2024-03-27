package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardsCollectionTest {
    private CardsCollection cardsCollection;

    @BeforeEach
    public void setup() {
        cardsCollection = new CardsCollection();
    }

    @Test
    public void testAddCard() {
        Card card = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        cardsCollection.addCard(card);
        assertEquals(1, cardsCollection.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, cardsCollection.size());
        Card card = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        cardsCollection.addCard(card);
        assertEquals(1, cardsCollection.size());
    }

    @Test
    public void testPopulateDeck() {
        // Assume that `cards.json` is a valid JSON file in the `src/test/resources` directory
        String jsonFilePath = "ing-sw-2024-pucci-santarossa-ruggieri-sciarretta/database/databaseGameCards.json";

        int initialSize = cardsCollection.size();

        // Call the method under test
        cardsCollection.populateDeck(jsonFilePath, "Resource");

        // Check that cards have been added to the collection
        //assertTrue(cardsCollection.size() =  40);
    }
    // Add more tests for other methods
}