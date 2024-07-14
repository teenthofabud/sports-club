package com.teenthofabud.sports.football;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssociationTest {
    private Association association;
    @BeforeEach
    void setUp() {
        association = Association.builder()
                .name("Test")
                .email("test@gmail.com")
                .website("test.com")
                .build();

    }
    @Test
    void testAddAssociation(){
        Association addedAssociation = Association.add(association);
        assertEquals(1L, addedAssociation.getId());
        assertTrue(Association.get().isEmpty());
    }
    @Test
    void testRemoveAssociation(){
        Association addAssociation = Association.add(association);
        Association removedAssociation = Association.remove(addAssociation.getId());
        assertEquals(association, removedAssociation);
    }
    @Test
    void testGetAssociation(){
        Association.add(association);
        Association retrieveAssociation = Association.get(association.getId());
        assertEquals(association, retrieveAssociation);
    }
    @Test
    void testEditName(){
        Association addedAssociation = Association.add(association);
        Association editedName = Association.editName(addedAssociation.getId(), association.getName());
        assertEquals("Test", editedName.getName());
    }
    @Test
    void testEditEmail(){
        Association addedAssociation = Association.add(association);
        Association editedEmail = Association.editEmail(addedAssociation.getId(), addedAssociation.getEmail());
        assertEquals("test@test.com", editedEmail.getEmail());
    }
    @Test
    void testEditWebsite(){
        Association addedAssociation = Association.add(association);
        Association editedWebsite = Association.editWebsite(addedAssociation.getId(), addedAssociation.getWebsite());
        assertEquals("testwebsite.com", editedWebsite.getWebsite());
    }
    @Test
    void testValidate(){
        Association invalidAssociation = Association.builder().name("").email("").website("").build();
        Exception exception = assertThrows(IllegalArgumentException.class, invalidAssociation::validate);
        assertEquals("Name cannot be empty", exception.getMessage());
    }

}