package com.test.travelplanner.repository;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.travelplanner.model.ForecastEntity;

@Repository
public interface ForecastRepository extends JpaRepository<ForecastEntity, UUID> {

  Optional<ForecastEntity>
      getFirstByCityNameAndForecastDateEqualsAndCreatedOnGreaterThanOrderByCreatedOnDesc(
          final String name,
          final Date date,
          final OffsetDateTime dateTime);

  List<ForecastEntity> getAllByIdIn(List<UUID> ids);
}
