package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.model.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<ClubEntity,Long> {
}
