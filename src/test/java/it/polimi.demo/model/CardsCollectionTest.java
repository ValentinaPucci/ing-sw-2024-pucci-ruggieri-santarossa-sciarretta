package it.polimi.demo.model;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;


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
        int initialSize = cardsCollection.size();

        // Call the method under test
        cardsCollection.populateDeck(getClass().getResourceAsStream("/database/databaseGameCards.json"), "Gold");
        cardsCollection.populateDeck(getClass().getResourceAsStream("/database/databaseGameCards.json"), "Resource");

    }
    @Test
    public void testPopulateDeckObjectiveCard() {
        // Assume that `cards.json` is a valid JSON file in the `src/test/resources` directory
        int initialSize = cardsCollection.size();
        assertEquals(0, initialSize);

        // Call the method under test
        cardsCollection.populateDeckObjective(getClass().getResourceAsStream("/database/databaseObjectiveCards.json"));

        for (Card card : cardsCollection.cards) {
            System.out.println(card.toString());
        }

        // Check that cards have been added to the collection
        assertTrue(cardsCollection.size()  ==  16);
    }

    @Test
    public void testPopulateDeckStarterFrontAndBack() {
        // Assume that `starterCards.json` is a valid JSON file in the `src/test/resources` directory
        int initialSize = cardsCollection.size();

        // Call the method under test
        cardsCollection.populateDeckStarterFrontAndBack(getClass().getResourceAsStream("/database/databaseStarterCards.json"));

        // Check that cards have been added to the collection
        assertTrue(cardsCollection.size() > 0);
        assertEquals(12, cardsCollection.size());

    }

    @Test
    public void getCards(){
        Card card = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        cardsCollection.addCard(card);
        assertEquals(1, cardsCollection.getCards().size());
    }
    @Test
    public void testAddNullCard() {
        assertThrows(NullPointerException.class, () -> {
            cardsCollection.addCard(null);
        });
    }

    @Test
    public void testGetCardsFromEmptyCollection() {
        assertTrue(cardsCollection.getCards().isEmpty());
    }

    @Test
    public void testPopulateDeckMultipleTimes() {
        String jsonFilePath = "database/databaseGameCards.json";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath);
        cardsCollection.populateDeck(inputStream, "Gold");
        int sizeAfterFirstPopulation = cardsCollection.size();
        cardsCollection.populateDeck(inputStream, "Gold");
        assertEquals(sizeAfterFirstPopulation, cardsCollection.size());
    }



}