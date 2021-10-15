package com.test.travelplanner.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class ForecastResponse {
  private String id;
  private String cityName;
  private String countryCode;
  private String clouds;
  private String temp;
  private String travelDate;
}
