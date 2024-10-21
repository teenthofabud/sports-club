package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.model.AddressEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;
    private AddressEntity addressEntity;
    @BeforeEach
    void setUp(){
        addressEntity = new AddressEntity("test streetname","test city","test state","4355","test type");
    }
    @Test
    void testCreate_Address_Success(){
        addressRepository.save(addressEntity);
        assertEquals(1, addressRepository.count());
    }
}