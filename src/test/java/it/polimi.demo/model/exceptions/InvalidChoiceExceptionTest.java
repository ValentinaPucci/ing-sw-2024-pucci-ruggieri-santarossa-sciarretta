package it.polimi.demo.model.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvalidChoiceExceptionTest {
    private InvalidChoiceException invalidChoiceException;

    @BeforeEach
    public void setUp() {
        invalidChoiceException = new InvalidChoiceException("Test");
    }

    @Test
    public void testInvalidChoiceException() {
        Assertions.assertNotNull(invalidChoiceException);
    }
}
