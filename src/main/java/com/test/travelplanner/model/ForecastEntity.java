package com.test.travelplanner.model;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "forecast")
public class ForecastEntity {
  @Id
  @GeneratedValue
  @Type(type="uuid-char")
  private UUID id;
  @Column(name = "city")
  private String cityName;
  @Column(name = "country_code")
  private String countryCode;
  @Column(name = "clouds")
  private String clouds;
  @Column(name = "temp")
  private String temp;
  @Column(name = "forecast_date")
  private Date forecastDate;
  @Column(name = "created_on")
  private OffsetDateTime createdOn = OffsetDateTime.now();
}
