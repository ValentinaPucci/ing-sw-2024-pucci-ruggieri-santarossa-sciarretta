package it.polimi.demo.model;

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
                cardsCollection.populateDeck("src/main/resources/database/databaseGameCards.json", "Resource");
                break;
            case "Gold":
                cardsCollection.populateDeck("src/main/resources/database/databaseGameCards.json", "Gold");
                break;
            case "Objective":
                cardsCollection.populateDeckObjective("src/main/resources/database/databaseObjectiveCards.json");
                break;
            case "Starter":
                cardsCollection.populateDeckStarterFrontAndBack("src/main/resources/database/databaseStarterCards.json");
                break;
        }
        return cardsCollection;
    }
}