package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Orientation;

public class ObjectiveFactory {
    /**
     * Metodo per creare un oggetto ObjectiveCard in base al tipo specificato
     * @param type il tipo di obiettivo da creare
     * @param id l'id della carta
     * @param orientation l'orientamento dell'obiettivo
     * @param points il punteggio dell'obiettivo
     * @return l'oggetto ObjectiveCard creato
     * @throws IllegalArgumentException se il tipo di obiettivo specificato non è valido
     */
    public static ObjectiveCard createObjectiveCard(String type, int id, Orientation orientation, int points) {
        switch (type) {
            case "Item":
                return new ItemObjectiveCard(id, orientation, points);
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

