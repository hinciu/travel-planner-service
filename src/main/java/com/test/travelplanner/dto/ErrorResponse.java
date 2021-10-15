package com.test.travelplanner.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private final String message;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<String> details;
}
