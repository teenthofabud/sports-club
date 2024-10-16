package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
