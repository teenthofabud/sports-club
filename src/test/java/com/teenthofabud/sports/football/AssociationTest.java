package com.teenthofabud.sports.football;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.TreeMap;

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
    @AfterEach
    void tearDown() throws Exception{
        resetStaticFields();
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
        Association editedName = Association.editName(addedAssociation.getId(), "Jane");
        assertEquals("Jane", editedName.getName());
    }
    @Test
    void testEditEmail(){
        Association addedAssociation = Association.add(association);
        Association editedEmail = Association.editEmail(addedAssociation.getId(), "test2@gmail.com");
        assertEquals("test2@test.com", editedEmail.getEmail());
    }
    @Test
    void testEditWebsite(){
        Association addedAssociation = Association.add(association);
        Association editedWebsite = Association.editWebsite(addedAssociation.getId(), "test3@gmail.com");
        assertEquals("test3website.com", editedWebsite.getWebsite());
    }
    @Test
    void testValidate(){
        Association invalidAssociation = Association.builder().name("").email("").website("").build();
        Exception exception = assertThrows(IllegalArgumentException.class, invalidAssociation::validate);
        assertEquals("Name cannot be empty", exception.getMessage());
    }
    private void resetStaticFields(){
        Collection<Association> associations = Association.get();
        for(Association association : associations){
            Association.remove(association.getId());
        }
        Association association = Association.builder().name("Rex").email("rex@gmail.com").website("rex.com").build();
        Association addedAssociation = Association.add(association);
        Association.remove(addedAssociation.getId());
    }
}