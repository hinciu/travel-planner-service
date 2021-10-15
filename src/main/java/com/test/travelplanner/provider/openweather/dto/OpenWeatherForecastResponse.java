package com.test.travelplanner.provider.openweather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenWeatherForecastResponse {
  private String name;
  private Sys sys;
  private Main main;
  private Clouds clouds;
}
