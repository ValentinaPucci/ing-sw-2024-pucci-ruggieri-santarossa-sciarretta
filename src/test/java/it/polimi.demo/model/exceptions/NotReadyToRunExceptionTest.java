package it.polimi.demo.model.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class NotReadyToRunExceptionTest {
    private NotReadyToRunException notReadyToRunException;

    @BeforeEach
    public void setUp() {
        notReadyToRunException = new NotReadyToRunException();
    }

    @Test
    public void testNotReadyToRunException() {
        Assertions.assertNotNull(notReadyToRunException);
    }
}
