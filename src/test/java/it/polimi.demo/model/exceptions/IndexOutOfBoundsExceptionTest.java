package it.polimi.demo.model.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IndexOutOfBoundsExceptionTest {
    private IndexOutOfBoundsException indexOutOfBoundsException;

    @BeforeEach
    public void setUp() {
        indexOutOfBoundsException = new IndexOutOfBoundsException();
    }

    @Test
    public void testIndexOutOfBoundsException() {
        Assertions.assertNotNull(indexOutOfBoundsException);
    }
}
