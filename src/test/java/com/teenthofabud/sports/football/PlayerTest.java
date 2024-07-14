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
    @Test
    void testEditFirstName(){
        Player editedName = Player.editFirstName(1L, "John");
        assertEquals("John", editedName.getFirstName());
    }
    @Test
    void testEditLastName(){
        Player editedName = Player.editLastName(1L, "Doe");
        assertEquals("Doe", editedName.getLastName());
    }
    @Test
    void testEditEmail(){
        Player editedEmail = Player.editEmail(1L, "john@doe.com");
        assertEquals("john@doe.com", editedEmail.getEmail());
    }
    @Test
    void testEditPhone(){
        Player editedPhone = Player.editPhoneNumber(1L, "0404059509504");
        assertEquals("0404059509504", editedPhone.getPhoneNumber());
    }

}