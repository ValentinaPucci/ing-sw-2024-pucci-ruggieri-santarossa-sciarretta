package it.polimi.ingsw.model;

public class ObjectiveFactory {

            public ObjectiveCard createObjectiveCard(String type, int id, Orientation orientation,int points) {
                switch (type) {
                    case "Item":
                        return new ItemObjectiveCard(id, orientation);
                    case "LetterPattern":
                        return new LetterPatternObjectiveCard(id, orientation, points);
                    case "DiagonalPattern":
                        return new DiagonalPatternObjectiveCard(id, orientation, points);
                    case "Resource":
                        return new ResourceObjectiveCard(id, orientation, points);
                    default:
                        throw new IllegalArgumentException("Invalid objective card type: " + type);
                }
            }
        }
