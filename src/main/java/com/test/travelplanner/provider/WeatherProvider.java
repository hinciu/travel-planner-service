package com.test.travelplanner.provider;

import com.test.travelplanner.dto.ForecastResponse;

public interface WeatherProvider {
  ForecastResponse  getForecastByCityName(final String cityName, final String date);
}
