package com.teenthofabud.sports.football;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;

    @Test
    void testAddPlayer(){
        Player addedPlayer = Player.add(player);
        assertNotNull(addedPlayer);
    }
    @Test
    void testRemovePlayer(){
        Player removedPlayer = Player.remove(player.getId());
        assertEquals(player, removedPlayer);
    }
    @Test
    void testGetId(){
        assertEquals(player.getId(), 1L);
    }

}