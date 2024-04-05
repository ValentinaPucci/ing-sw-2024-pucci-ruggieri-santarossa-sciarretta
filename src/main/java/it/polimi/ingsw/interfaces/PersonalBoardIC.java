package it.polimi.ingsw.interfaces;

import it.polimi.ingsw.model.cards.gameCards.GoldCard;
import it.polimi.ingsw.model.cards.gameCards.ResourceCard;
import it.polimi.ingsw.model.cards.gameCards.StarterCard;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;

public interface PersonalBoardIC {

    void bruteForcePlaceCardSE(StarterCard card, int i, int j);
    void placeCardAtNE(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;
    void placeCardAtNW(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;
    void placeCardAtSW(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;
    void placeCardAtSE(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;
    void placeCardAtNE(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;
    void placeCardAtNW(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;
    void placeCardAtSW(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;
    void placeCardAtSE(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;

}
