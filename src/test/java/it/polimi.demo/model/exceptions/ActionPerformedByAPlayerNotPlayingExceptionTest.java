package it.polimi.demo.model.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionPerformedByAPlayerNotPlayingExceptionTest {
    private ActionPerformedByAPlayerNotPlayingException actionException;

    @BeforeEach
    public void setUp() {
        this.actionException = new ActionPerformedByAPlayerNotPlayingException();
    }

    @Test
    public void testActionPerformedByAPlayerNotPlayingException() {
        Assertions.assertNotNull(this.actionException);
    }

}
