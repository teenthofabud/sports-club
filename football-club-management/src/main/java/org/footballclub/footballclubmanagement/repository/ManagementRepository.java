package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.model.ManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagementRepository extends JpaRepository<ManagementEntity, Long> {
}
