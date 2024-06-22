package it.polimi.demo.model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void testPlayerGeneric() {
        Player player = new Player("nickname");
        assertEquals("nickname", player.getNickname());

        player.setNickname("newNickname");
        assertEquals("newNickname", player.getNickname());

        List<Integer> cards_ids = player.getCardHandIds();
        assertEquals(0, cards_ids.size());
    }
}
