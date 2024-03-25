package it.polimi.ingsw.model;

public class DeckFactory {
    public CardsCollection createDeck(String type) {
        CardsCollection cardsCollection = new CardsCollection();
        switch (type) {
            case "Resource":
                cardsCollection.populateDeck("database/databaseGameCards.json", "Resource");
                break;
            case "Gold":
                cardsCollection.populateDeck("database/databaseGameCards.json", "Gold");
                break;
            case "Objective":
                cardsCollection.populateDeckObjective("database/databaseObjectiveCards.json");
                break;
            case "Starter":
                cardsCollection.populateDeckStarterFrontAndBack("database/databaseStarterCards.json");
                break;
        }
        return cardsCollection;
    }
}