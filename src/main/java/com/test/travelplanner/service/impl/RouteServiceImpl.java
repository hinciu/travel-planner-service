package com.test.travelplanner.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.travelplanner.dto.CreateRouteRequest;
import com.test.travelplanner.dto.ForecastResponse;
import com.test.travelplanner.dto.GetRouteResponse;
import com.test.travelplanner.exception.ResourceNotFoundException;
import com.test.travelplanner.exception.RouteException;
import com.test.travelplanner.mapper.ForecastMapper;
import com.test.travelplanner.model.RouteEntity;
import com.test.travelplanner.repository.ForecastRepository;
import com.test.travelplanner.repository.RouteRepository;
import com.test.travelplanner.service.RouteService;

import lombok.AllArgsConstructor;
import lombok.val;

@Service
@AllArgsConstructor
public class RouteServiceImpl implements RouteService {

  private static final String RECOMMENDATION_MESSAGE = "Please take a coat";
  private static final int LOW_TEMPERATURE = 5;


  private final ForecastRepository forecastRepository;
  private final RouteRepository routeRepository;

  @Override
  public void createRoute(final CreateRouteRequest createRouteRequest) {
    val routeName = createRouteRequest.getRouteName();
    if (routeRepository.findAllByName(routeName).isPresent()) {
      throw new RouteException("Route with name: " + routeName + " already exists");
    }

    val forecastIds = createRouteRequest.getForecastIds().stream().map(UUID::fromString)
        .collect(Collectors.toList());

    val newRoute = new RouteEntity();
    newRoute.setName(routeName);
    newRoute.setForecastList(forecastIds);
    routeRepository.save(newRoute);
  }

  @Override
  public GetRouteResponse getRouteByName(final String name) {
    val route = routeRepository.findAllByName(name);
    if (route.isEmpty()) {
      throw new ResourceNotFoundException("Route with name: " + name + " not found");
    }

    val routeEntities = forecastRepository.getAllByIdIn(route.get().getForecastList());

    val cities = routeEntities.stream().map(ForecastMapper::entityToDTO)
        .collect(Collectors.toList());

    val recommendations = generateRecommendations(cities);

    return GetRouteResponse.builder()
        .name(name)
        .cities(cities)
        .recommendations(recommendations.orElse(null))
        .build();
  }

  private Optional<String> generateRecommendations(List<ForecastResponse> cities) {
    val coldCities =
        cities.stream().filter(c -> Float.parseFloat(c.getTemp()) < LOW_TEMPERATURE).findAny();
    if (coldCities.isPresent()) {
      return Optional.of(RECOMMENDATION_MESSAGE);
    }
    return Optional.empty();
  }
}
