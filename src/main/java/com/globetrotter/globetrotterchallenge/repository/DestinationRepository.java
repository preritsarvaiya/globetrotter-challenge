package com.globetrotter.globetrotterchallenge.repository;

import com.globetrotter.globetrotterchallenge.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    @Query(value = "SELECT * FROM destination ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Destination findRandom();

    @Query(value = "SELECT * FROM destination WHERE id != ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
    List<Destination> findRandomExcluding(Long excludeId, int limit);

    long count();
}