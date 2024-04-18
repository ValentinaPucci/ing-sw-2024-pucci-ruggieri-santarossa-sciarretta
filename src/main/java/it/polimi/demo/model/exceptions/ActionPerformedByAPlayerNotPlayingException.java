package it.polimi.demo.model.exceptions;

/**
 * This exception is thrown when a player that is not playing tries to perform an action
 */
public class ActionPerformedByAPlayerNotPlayingException extends RuntimeException {
    public ActionPerformedByAPlayerNotPlayingException() {
        super();
    }
}
