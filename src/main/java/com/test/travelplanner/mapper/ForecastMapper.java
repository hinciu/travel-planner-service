package com.test.travelplanner.mapper;

import org.springframework.beans.BeanUtils;

import com.test.travelplanner.dto.ForecastResponse;
import com.test.travelplanner.model.ForecastEntity;

import lombok.val;

public class ForecastMapper {
  public static ForecastResponse entityToDTO(final ForecastEntity entity) {
    val response = ForecastResponse.builder().build();
    BeanUtils.copyProperties(entity, response);
    response.setId(String.valueOf(entity.getId()));
    response.setTravelDate(String.valueOf(entity.getForecastDate()));
    return response;
  }

  public static ForecastEntity dtoToEntity(final ForecastResponse responseDTO) {
    val newForecastEntity = new ForecastEntity();
    BeanUtils.copyProperties(responseDTO, newForecastEntity);
    return newForecastEntity;
  }
}
