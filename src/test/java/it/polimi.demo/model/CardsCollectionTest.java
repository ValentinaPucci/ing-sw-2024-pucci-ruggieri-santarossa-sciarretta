package it.polimi.demo.model;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        cardsCollection.populateDeck(jsonFilePath, "Resource");

    }
    @Test
    public void testPopulateDeckObjectiveCard() {
        // Assume that `cards.json` is a valid JSON file in the `src/test/resources` directory
        String jsonFilePath = "src/main/resources/database/databaseObjectiveCards.json";

        int initialSize = cardsCollection.size();
        assertEquals(0, initialSize);

        // Call the method under test
        cardsCollection.populateDeckObjective(jsonFilePath);

        for (Card card : cardsCollection.cards) {
            System.out.println(card.toString());
        }

        // Check that cards have been added to the collection
        assertTrue(cardsCollection.size()  ==  16);
    }

    @Test
    public void testPopulateDeckStarterFrontAndBack() {
        // Assume that `starterCards.json` is a valid JSON file in the `src/test/resources` directory
        String jsonFilePath = "src/main/resources/database/databaseStarterCards.json";

        int initialSize = cardsCollection.size();

        // Call the method under test
        cardsCollection.populateDeckStarterFrontAndBack(jsonFilePath);

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
    public void testPopulateDeckWithInvalidFilePath() {
        // Prepare to capture System.err output
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        // Call the method under test
        cardsCollection.populateDeck("invalid/file/path.json", "Gold");

        // Check that the expected error message was printed
        assertTrue(errContent.toString().contains("Error populating deck: invalid/file/path.json (No such file or directory)"));

        // Reset System.err to its original stream
        System.setErr(System.err);
    }
    @Test
    public void populateDeckObjectiveWithInvalidFilePath() {
        // Prepare to capture System.err output
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        // Call the method under test
        cardsCollection.populateDeckObjective("invalid/file/path.json");

        // Check that the expected error message was printed
        assertTrue(errContent.toString().contains("Error populating objective cards deck: invalid/file/path.json (No such file or directory)"));

        // Reset System.err to its original stream
        System.setErr(System.err);
    }

    public void populateDeckStarterFrontAndBackWithInvalidFilePath() {
        // Prepare to capture System.err output
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        // Call the method under test
        cardsCollection.populateDeckStarterFrontAndBack("invalid/file/path.json");

        // Check that the expected error message was printed
        assertTrue(errContent.toString().contains("Error populating starter cards deck: invalid/file/path.json (No such file or directory)"));

        // Reset System.err to its original stream
        System.setErr(System.err);
    }

    @Test
    public void testGetCardsFromEmptyCollection() {
        assertTrue(cardsCollection.getCards().isEmpty());
    }

    @Test
    public void testPopulateDeckMultipleTimes() {
        String jsonFilePath = "src/main/resources/database/databaseGameCards.json";
        cardsCollection.populateDeck(jsonFilePath, "Gold");
        int sizeAfterFirstPopulation = cardsCollection.size();
        cardsCollection.populateDeck(jsonFilePath, "Gold");
        assertEquals(sizeAfterFirstPopulation, cardsCollection.size());
    }



}