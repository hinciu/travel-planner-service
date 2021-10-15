package com.test.travelplanner.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.travelplanner.dto.ForecastResponse;
import com.test.travelplanner.service.WeatherService;
import com.test.travelplanner.validator.ValidDateFormat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(WeatherController.BASE_URL)
public class WeatherController {
  public static final String BASE_URL = "/travel-planner-service/";
  public static final String FORECAST_URL = "/forecast/{cityName}";

  private final WeatherService weatherService;

  @GetMapping(FORECAST_URL)
  public ResponseEntity<ForecastResponse> getForecastByCityName(
      @PathVariable @NotBlank final String cityName,
      @ValidDateFormat @RequestParam("date") String date) {
    val response = weatherService.getForecastByCityName(cityName, date);
    return ResponseEntity.ok(response);
  }

}
