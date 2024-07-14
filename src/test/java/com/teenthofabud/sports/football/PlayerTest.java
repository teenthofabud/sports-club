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
        Player editedName = Player.editFirstName(player.getId(), "Mike");
        assertEquals("Mike", editedName.getFirstName());
    }
    @Test
    void testEditLastName(){
        Player addedPlayer = Player.add(player);
        Player editedName = Player.editLastName(addedPlayer.getId(), "Raye");
        assertEquals("Raye", editedName.getLastName());
    }
    @Test
    void testEditEmail(){
        Player addedPlayer = Player.add(player);
        Player editedEmail = Player.editEmail(addedPlayer.getId(), "raye@gmail.com");
        assertEquals("Raye@gmail.com", editedEmail.getEmail());
    }
    @Test
    void testEditPhone(){
        Player addedPlayer = Player.add(player);
        Player editedPhone = Player.editPhoneNumber(addedPlayer.getId(), "024567772345");
        assertEquals("024567772345", editedPhone.getPhoneNumber());
    }

}