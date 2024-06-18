package it.polimi.demo.model.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class NotEmptyHandExceptionTest {
    private NotEmptyHandException notEmptyHandException;

    @BeforeEach
    public void setUp() {
        notEmptyHandException = new NotEmptyHandException();
    }

    @Test
    public void testNotEmptyHandException() {
        Assertions.assertNotNull(notEmptyHandException);
    }
}
