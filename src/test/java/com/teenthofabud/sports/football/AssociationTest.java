package com.teenthofabud.sports.football;

import static org.junit.jupiter.api.Assertions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class AssociationTest {
    private Association association;
    @Test
    void testAddAssociation(){
        Association addedAssociation = Association.add(association);
        assertEquals(1L, addedAssociation.getId());
        assertTrue(Association.get().isEmpty());
    }
    @Test
    void testRemoveAssociation(){
        Association removedAssociation = Association.remove(1L);
        assertEquals(association, removedAssociation);
    }
    @Test
    void testGetAssociation(){
        Association.add(association);
        Association retrieveAssociation = Association.get(1L);
        assertEquals(association, retrieveAssociation);
    }
    @Test
    void testEditName(){
        Association.add(association);
        Association editedName = Association.editName(1L, "Test");
        assertEquals("Test", editedName.getName());
    }
    @Test
    void testEditEmail(){
        Association.add(association);
        Association editedEmail = Association.editEmail(1L, "test@test.com");
        assertEquals("test@test.com", editedEmail.getEmail());
    }
    @Test
    void testEditWebsite(){
        Association.add(association);
        Association editedWebsite = Association.editWebsite(1L, "testwebsite.com");
        assertEquals("testwebsite.com", editedWebsite.getWebsite());
    }
    @Test
    void testValidate(){
        Association invalidAssociation = Association.builder().name("").email("").website("").build();
        Exception exception = assertThrows(IllegalArgumentException.class, invalidAssociation::validate);
        assertEquals("Name cannot be empty", exception.getMessage());
    }

}