package org.footballclub.footballclubmanagement.repository;

import org.footballclub.footballclubmanagement.model.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubRepository extends JpaRepository<ClubEntity,Long> {
    @Query("SELECT c FROM ClubEntity c WHERE c.name LIKE %:name% AND c.email LIKE %:email% AND c.abbreviation LIKE %:abbreviation%")
    Page<ClubEntity> findByFilter(@Param("name") String name,
                                  @Param("email") String email,
                                  @Param("abbreviation") String abbreviation,
                                  Pageable pageable);
}
