package it.polimi.demo.model.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class IllegalMoveExceptionTest {
    private IllegalMoveException illegalMoveException;

    @BeforeEach
    public void setUp() {
        illegalMoveException = new IllegalMoveException();
    }

    @Test
    public void testIllegalMoveException() {
        Assertions.assertNotNull(illegalMoveException);
    }
}
