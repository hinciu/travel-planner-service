package com.test.travelplanner;

import static com.test.travelplanner.controller.WeatherController.BASE_URL;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.test.travelplanner.exception.ProviderException;
import com.test.travelplanner.provider.openweather.OpenWeatherConnectionClient;
import com.test.travelplanner.provider.openweather.dto.OpenWeatherForecastResponse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherControllerIT extends BaseIT {

  private final RestTemplate template = mock(RestTemplate.class);

  @Autowired
  private OpenWeatherConnectionClient openWeatherConnectionClient;

  @BeforeAll
  void init()  {
    ReflectionTestUtils.setField(openWeatherConnectionClient, "restTemplate", template);
  }

  @Test
  @Sql(scripts = "classpath:sql/clearTables.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
      config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  void testGetForecast_Success() throws Exception {
    Mockito.when(template.getForObject(anyString(), eq(OpenWeatherForecastResponse.class)))
        .thenReturn(readExpectedResponse("openweather-forecast-response",
            OpenWeatherForecastResponse.class));
    mockMvc
        .perform(get(BASE_URL + "/forecast/London?date=2021-01-01")
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("cityName").value("London"))
        .andExpect(jsonPath("countryCode").value("GB"))
        .andExpect(jsonPath("clouds").value("90"))
        .andExpect(jsonPath("temp").value("8.81"));
  }

  @Test
  @Sql(scripts = "classpath:sql/clearTables.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
      config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  void testGetForecastFromCache_Error() throws Exception {
    Mockito.when(template.getForObject(anyString(), eq(OpenWeatherForecastResponse.class)))
        .thenThrow(new ProviderException("Error"));

    mockMvc
        .perform(get(BASE_URL + "/forecast/london?date=2021-01-01")
            .contentType(APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("message").value("Error"));
  }

}
