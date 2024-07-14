package com.teenthofabud.sports.football;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    @BeforeEach
    void setUp() {
        player = Player.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("0799912938")
                .build();
    }

    @Test
    void testAddPlayer(){
        Player addedPlayer = Player.add(player);
        assertNotNull(addedPlayer);
    }
    @Test
    void testRemovePlayer(){
        Player addedPlayer = Player.add(player);
        Player removedPlayer = Player.remove(addedPlayer.getId());
        assertEquals(player, removedPlayer);
    }
    @Test
    void testEditFirstName(){
        Player addedPlayer = Player.add(player);
        Player editedName = Player.editFirstName(player.getId(), player.getFirstName());
        assertEquals("John", editedName.getFirstName());
    }
    @Test
    void testEditLastName(){
        Player addedPlayer = Player.add(player);
        Player editedName = Player.editLastName(addedPlayer.getId(), player.getLastName());
        assertEquals("Doe", editedName.getLastName());
    }
    @Test
    void testEditEmail(){
        Player addedPlayer = Player.add(player);
        Player editedEmail = Player.editEmail(addedPlayer.getId(), addedPlayer.getEmail());
        assertEquals("john@doe.com", editedEmail.getEmail());
    }
    @Test
    void testEditPhone(){
        Player addedPlayer = Player.add(player);
        Player editedPhone = Player.editPhoneNumber(addedPlayer.getId(), addedPlayer.getPhoneNumber());
        assertEquals("0404059509504", editedPhone.getPhoneNumber());
    }

}