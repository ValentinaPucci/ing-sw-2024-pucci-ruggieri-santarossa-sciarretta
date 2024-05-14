package it.polimi.demo.model;

import java.io.Serializable;

public class DeckFactory implements Serializable {
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