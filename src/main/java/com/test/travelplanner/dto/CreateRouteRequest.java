package com.test.travelplanner.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.test.travelplanner.validator.ValidUUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRouteRequest {
  @NotEmpty
  List<@ValidUUID String> forecastIds;
  @NotBlank
  private String routeName;
}
