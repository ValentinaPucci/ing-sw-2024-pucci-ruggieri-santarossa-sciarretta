package it.polimi.demo.model.exceptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameNotStartedExceptionTest {
    private GameNotStartedException gameNotStartedException;

    @BeforeEach
    public void setUp() {
        gameNotStartedException = new GameNotStartedException();
    }

    @Test
    public void testGameNotStartedException() {
        Assertions.assertNotNull(gameNotStartedException);
    }
}
