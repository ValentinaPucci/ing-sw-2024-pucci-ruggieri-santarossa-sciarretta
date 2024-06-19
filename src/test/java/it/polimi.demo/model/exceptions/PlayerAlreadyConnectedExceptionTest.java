package it.polimi.demo.model.exceptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class PlayerAlreadyConnectedExceptionTest {
    private PlayerAlreadyConnectedException playerAlreadyConnectedException;

    @BeforeEach
    public void setUp() {
        playerAlreadyConnectedException = new PlayerAlreadyConnectedException();
    }

    @Test
    public void testPlayerAlreadyConnectedException() {
        Assertions.assertNotNull(playerAlreadyConnectedException);
    }

}
