package com.test.travelplanner.service;

import com.test.travelplanner.dto.ForecastResponse;

public interface WeatherService {
  ForecastResponse getForecastByCityName(final String cityName, String date);
}
