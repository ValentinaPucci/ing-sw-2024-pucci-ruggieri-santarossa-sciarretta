package it.polimi.ingsw.model;

public class ObjectiveFactory {

    /*
    todo: fix the constructor methods in ItemObjectiveCard, LetterPatternObjectiveCard, DiagonalPatternObjectiveCard,
     ResourceObjectiveCard classes
     */
            public ObjectiveCard createObjectiveCard(String type) {
                switch (type) {
                    case "Item":
                        return new ItemObjectiveCard();
                    case "LetterPattern":
                        return new LetterPatternObjectiveCard();
                    case "DiagonalPattern":
                        return new DiagonalPatternObjectiveCard();
                    case "Resource":
                        return new ResourceObjectiveCard();
                    default:
                        throw new IllegalArgumentException("Invalid objective card type: " + type);
                }
            }
        }
