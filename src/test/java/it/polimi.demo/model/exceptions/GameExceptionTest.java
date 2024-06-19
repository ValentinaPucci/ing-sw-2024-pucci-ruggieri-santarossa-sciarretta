package it.polimi.demo.model.exceptions;

import it.polimi.demo.model.exceptions.GameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameExceptionTest {
    private GameException gameException;

    // Creazione di una sottoclasse concreta di GameException per i test
    private static class TestableGameException extends GameException {
        public TestableGameException(String s) {
            super(s);
        }
    }

    @BeforeEach
    public void setUp() {
        this.gameException = new TestableGameException("Test exception message");
    }

    @Test
    public void testGameException() {
        Assertions.assertNotNull(this.gameException);
    }
}
