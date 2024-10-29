package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.model.ClubEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class ClubRepositoryTest {
    @Autowired
    private ClubRepository clubRepository;

    private ClubEntity clubEntity;


    @BeforeEach
    void setUp() {
        clubEntity = new ClubEntity(
                "test club name",
                "test email",
                "abv",
                "012456254631",
                "logo.jpg",
                "club.com"
        );

    }
    @Test
    void testCreate_Club_Null_Management_Id_Failure(){
        clubEntity.setManagement(null);
        assertThrows(DataIntegrityViolationException.class, () -> clubRepository.save(clubEntity));
    }
    @Test
    void testCreate_Club_Null_Level_Id_Failure(){
        clubEntity.setLevel(null);
        assertThrows(DataIntegrityViolationException.class, () -> clubRepository.save(clubEntity));
    }
    @Test
    void testCreate_Club_Null_Address_Id_Failure(){
        clubEntity.setAddress(null);
        assertThrows(DataIntegrityViolationException.class, () -> clubRepository.save(clubEntity));
    }


}