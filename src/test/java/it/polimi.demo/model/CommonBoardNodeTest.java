package it.polimi.demo.model;

import it.polimi.demo.model.board.CommonBoardNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommonBoardNodeTest {
    private CommonBoardNode node;

    public CommonBoardNodeTest() {
    }

    @BeforeEach
    public void setUp() {
        this.node = new CommonBoardNode(1);
    }

    @Test
    public void testSetPlayer() {
        this.node.setPlayer(2, true);
        Assertions.assertTrue(this.node.isPlayerPresent(2));
    }

    @Test
    public void testIsPlayerPresent() {
        this.node.setPlayer(1, true);
        Assertions.assertTrue(this.node.isPlayerPresent(1));
    }

    @Test
    public void testIsPlayerNotPresent() {
        Assertions.assertFalse(this.node.isPlayerPresent(3));
    }

    @Test
    public void testGetNodeNumber() {
        Assertions.assertEquals(1, this.node.getNodeNumber());
    }
}
