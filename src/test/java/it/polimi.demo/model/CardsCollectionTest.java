package it.polimi.demo.model;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;
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
        String jsonFilePath = "src/main/resources/database/databaseGameCards.json";

        int initialSize = cardsCollection.size();

        // Call the method under test
        cardsCollection.populateDeck(jsonFilePath, "Gold");

        // Check that cards have been added to the collection
        //assertTrue(cardsCollection.size() =  40);
    }
    // Add more tests for other methods
    @Test
    public void testPopulateDeckObjectiveCard() {
        // Assume that `cards.json` is a valid JSON file in the `src/test/resources` directory
        String jsonFilePath = "src/main/resources/database/databaseObjectiveCards.json";

        int initialSize = cardsCollection.size();

        // Call the method under test
        cardsCollection.populateDeckObjective(jsonFilePath);

        // Check that cards have been added to the collection
        assertTrue(cardsCollection.size()  ==  16);
    }

//    @Test
//    public void testPopulateDeckStarterFrontAndBack() {
//        // Assume that `starterCards.json` is a valid JSON file in the `src/test/resources` directory
//        String jsonFilePath = "src/main/resources/database/databaseStarterCards.json";
//
//        int initialSize = cardsCollection.size();
//
//        // Call the method under test
//        cardsCollection.populateDeckStarterFrontAndBack(jsonFilePath);
//
//        // Check that cards have been added to the collection
//        assertTrue(cardsCollection.size() > 0);
//    }



}