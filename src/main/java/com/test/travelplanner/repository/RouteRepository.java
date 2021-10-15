package com.test.travelplanner.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.travelplanner.model.RouteEntity;

public interface RouteRepository extends JpaRepository<RouteEntity, UUID> {
  Optional<RouteEntity> findAllByName(final String name);
}
