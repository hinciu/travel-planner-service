package com.test.travelplanner.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetRouteResponse {
  private String name;
  private List<ForecastResponse> cities;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String recommendations;
}
