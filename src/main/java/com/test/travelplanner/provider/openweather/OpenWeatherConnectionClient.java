package com.test.travelplanner.provider.openweather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.test.travelplanner.exception.ProviderException;
import com.test.travelplanner.provider.openweather.dto.OpenWeatherForecastResponse;

@Component
public class OpenWeatherConnectionClient {

  private final RestTemplate restTemplate;
  @Value("${connection.params.openweather.url}")
  private String url;

  public OpenWeatherConnectionClient() {
    this.restTemplate = new RestTemplate();
  }


  public OpenWeatherForecastResponse getWeatherData(final String name) {

    try {
      return restTemplate
          .getForObject(url.replace("{city}", name), OpenWeatherForecastResponse.class);
    } catch (RestClientException exception) {
      throw new ProviderException(exception.getMessage());
    }
  }
}
