package com.test.travelplanner.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "route")
public class RouteEntity {
  @Id
  @GeneratedValue
  private UUID id;
  @Column(name = "name", unique = true)
  private String name;
  @ElementCollection
  private List<UUID> forecastList;
}
