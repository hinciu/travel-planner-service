package com.test.travelplanner.controller;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.travelplanner.dto.CreateRouteRequest;
import com.test.travelplanner.dto.GetRouteResponse;
import com.test.travelplanner.service.RouteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(WeatherController.BASE_URL)
public class RouteController {
  public static final String ROUTE_URL = "/route";
  public static final String GET_ROUTE = "/{name}";

  private final RouteService routeService;

  @PostMapping(ROUTE_URL)
  public ResponseEntity<Void>
      createRoute(@Valid @RequestBody final CreateRouteRequest createRouteRequest) {
    routeService.createRoute(createRouteRequest);
    return ResponseEntity.status(CREATED).body(null);
  }

  @GetMapping(ROUTE_URL + GET_ROUTE)
  public ResponseEntity<GetRouteResponse>
      getResourceByName(@PathVariable @NotBlank final String name) {
    val response = routeService.getRouteByName(name);
    return ResponseEntity.ok(response);
  }
}
