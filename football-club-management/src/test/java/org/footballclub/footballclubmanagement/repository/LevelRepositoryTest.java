package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.config.TestConfig;
import org.footballclub.footballclubmanagement.model.LevelEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@Import(TestConfig.class)
class LevelRepositoryTest {
    @Autowired
    private LevelRepository levelRepository;

    private LevelEntity levelEntity;

    @BeforeEach
    void setUp() {
        levelEntity = new LevelEntity("test level name");
    }

    @Test
    void testCreate_Level_Name(){
        levelRepository.save(levelEntity);
        assertEquals(1,levelRepository.count());
    }
}