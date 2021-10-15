package com.test.travelplanner.service.impl;

import java.sql.Date;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.travelplanner.dto.ForecastResponse;
import com.test.travelplanner.mapper.ForecastMapper;
import com.test.travelplanner.provider.WeatherProvider;
import com.test.travelplanner.repository.ForecastRepository;
import com.test.travelplanner.service.WeatherService;

import lombok.val;

@Service
public class WeatherServiceImpl implements WeatherService {
  @Value("${forecast_cache_time}")
  private long cacheTime;

  private final WeatherProvider weatherProvider;
  private final ForecastRepository forecastRepository;

  @Autowired
  public WeatherServiceImpl(final WeatherProvider weatherProvider, final ForecastRepository forecastRepository) {
    this.weatherProvider = weatherProvider;
    this.forecastRepository = forecastRepository;
  }

  @Override
  public ForecastResponse getForecastByCityName(final String cityName, final String date) {
    val forecastDate = Date.valueOf(date);
    val cacheDateTime = OffsetDateTime.now().minusMinutes(cacheTime);
    val cachedResult =
        forecastRepository
            .getFirstByCityNameAndForecastDateEqualsAndCreatedOnGreaterThanOrderByCreatedOnDesc(
                cityName, forecastDate,
                cacheDateTime);

    if (cachedResult.isEmpty()) {
      val actualForecast = weatherProvider.getForecastByCityName(cityName, date);
      val entity = ForecastMapper.dtoToEntity(actualForecast);
      entity.setForecastDate(forecastDate);
      forecastRepository.save(entity);
      actualForecast.setId(String.valueOf(entity.getId()));
      actualForecast.setTravelDate(date);
      return actualForecast;
    }

    return ForecastMapper.entityToDTO(cachedResult.get());
  }
}
