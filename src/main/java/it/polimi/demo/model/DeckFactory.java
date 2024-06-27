package it.polimi.demo.model;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Class the implements the Factory design pattern to create the different types of decks, given the different paths of the JSON
 * files of the different types of cards.

 */

public class DeckFactory implements Serializable {

    /**
     * Method that creates the deck of the specified type.
     * @param type the type of deck to create
     * @return the deck of the specified type
     */
    public CardsCollection createDeck(String type) {
        CardsCollection cardsCollection = new CardsCollection();
        switch (type) {
            case "Resource":
                cardsCollection.populateDeck(getResourceAsStream("/database/databaseGameCards.json"), "Resource");
                break;
            case "Gold":
                cardsCollection.populateDeck(getResourceAsStream("/database/databaseGameCards.json"), "Gold");
                break;
            case "Objective":
                cardsCollection.populateDeckObjective(getResourceAsStream("/database/databaseObjectiveCards.json"));
                break;
            case "Starter":
                cardsCollection.populateDeckStarterFrontAndBack(getResourceAsStream("/database/databaseStarterCards.json"));
                break;
        }
        return cardsCollection;
    }

    /**
     * Helper method to get InputStream from resource path.
     * @param resourcePath the path of the resource
     * @return InputStream of the resource
     */
    private InputStream getResourceAsStream(String resourcePath) {
        return getClass().getResourceAsStream(resourcePath);
    }
}