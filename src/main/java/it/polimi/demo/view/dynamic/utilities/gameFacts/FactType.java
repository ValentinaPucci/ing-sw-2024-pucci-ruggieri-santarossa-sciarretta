package it.polimi.demo.view.dynamic.utilities.gameFacts;

/**
 * This enum represents the type of fact that can be sent to the view
 */
public enum FactType {
    LOBBY_INFO,
    PLAYER_JOINED,
    FULL_GAME,
    ALREADY_USED_NICKNAME,
    GENERIC_ERROR,
    PLAYER_IS_READY_TO_START,
    GAME_STARTED,
    GAME_ENDED,
    ASK_WHICH_ORIENTATION,
    ILLEGAL_MOVE,
    CARD_PLACED,
    NEXT_TURN,
}
