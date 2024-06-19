package it.polimi.demo.model.exceptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class MaxPlayersLimitExceptionTest {
    private MaxPlayersLimitException maxPlayersLimitException;

    @BeforeEach
    public void setUp() {
        maxPlayersLimitException = new MaxPlayersLimitException();
    }

    @Test
    public void testMaxPlayersLimitException() {
        Assertions.assertNotNull(maxPlayersLimitException);
    }
}
