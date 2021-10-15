package com.test.travelplanner.provider.openweather;

import org.springframework.stereotype.Component;

import com.test.travelplanner.dto.ForecastResponse;
import com.test.travelplanner.provider.WeatherProvider;

import lombok.AllArgsConstructor;
import lombok.val;

@Component
@AllArgsConstructor
public class OpenWeatherProvider implements WeatherProvider {

  private final OpenWeatherConnectionClient connectionClient;

  @Override
  // OpenWeather does not provide functionality to obtain forecast for particular date.
  // Maybe others paid providers gives such opportunity. For now lets assume that forecast is provided for
  // passed date
  public ForecastResponse getForecastByCityName(final String cityName, final String date) {

    val response = connectionClient.getWeatherData(cityName);

    return ForecastResponse.builder()
        .cityName(cityName)
        .clouds(response.getClouds().getAll())
        .countryCode(response.getSys().getCountry())
        .temp(response.getMain().getTemp())
        .build();
  }
}
