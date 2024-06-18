package it.polimi.demo.model.exceptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class GameEndedExceptionTest {
    private GameEndedException gameEndedException;

    @BeforeEach
    public void setUp() {
        this.gameEndedException = new GameEndedException();
    }

    @Test
    public void testGameEndedException() {
        Assertions.assertNotNull(this.gameEndedException);
    }
}
