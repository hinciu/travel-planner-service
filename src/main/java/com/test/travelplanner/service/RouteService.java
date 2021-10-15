package com.test.travelplanner.service;

import com.test.travelplanner.dto.CreateRouteRequest;
import com.test.travelplanner.dto.GetRouteResponse;

public interface RouteService {
  void createRoute(final CreateRouteRequest createRouteRequest);

  GetRouteResponse getRouteByName(final String name);
}
