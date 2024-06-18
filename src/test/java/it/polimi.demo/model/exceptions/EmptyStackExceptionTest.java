package it.polimi.demo.model.exceptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmptyStackExceptionTest {
    private EmptyStackException emptyStackException;

    @BeforeEach
    public void setUp() {
        this.emptyStackException = new EmptyStackException();
    }

    @Test
    public void testEmptyStackException() {
        Assertions.assertNotNull(this.emptyStackException);
    }
}
