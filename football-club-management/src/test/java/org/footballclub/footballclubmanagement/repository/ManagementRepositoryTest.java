package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.config.TestConfig;
import org.footballclub.footballclubmanagement.model.ManagementEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Import(TestConfig.class)
class ManagementRepositoryTest {
    @Autowired
    private ManagementRepository managementRepository;

    private ManagementEntity managementEntity;
    @BeforeEach
    void setUp() {
        managementEntity = new ManagementEntity("management name","0553959392");
    }

    @Test
    void testCreate_Management(){
        managementRepository.save(managementEntity);
        assertEquals(1, managementRepository.count());
    }
}