package org.footballclub.footballclubmanagement.repository;

import lombok.extern.slf4j.Slf4j;
import org.footballclub.footballclubmanagement.model.StadiumEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StadiumRepositoryTest {
    @Autowired
    private StadiumRepository stadiumRepository;

    private StadiumEntity stadiumEntity;
    @BeforeEach
    void setUp() {
        stadiumEntity = new StadiumEntity("test stadium name",
                                          "test type");
    }
    @Transactional
    @Test
    void testCreate_Stadium_Details_Null_Address_Id(){
        stadiumEntity.setAddress(null);
        stadiumEntity.setClub(null);

        assertThrows(DataIntegrityViolationException.class, () -> {
            stadiumRepository.save(stadiumEntity);
            stadiumRepository.flush();
        });
    }
}