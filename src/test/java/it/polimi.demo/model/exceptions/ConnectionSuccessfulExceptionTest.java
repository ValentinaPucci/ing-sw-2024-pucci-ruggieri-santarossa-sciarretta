package it.polimi.demo.model.exceptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionSuccessfulExceptionTest {
    private ConnectionSuccessfulException connectionException;

    @BeforeEach
    public void setUp() {
        this.connectionException = new ConnectionSuccessfulException();
    }

    @Test
    public void testConnectionSuccessfulException() {
        Assertions.assertNotNull(this.connectionException);
    }
}
