package com.test.travelplanner;

import static com.test.travelplanner.controller.RouteController.ROUTE_URL;
import static com.test.travelplanner.controller.WeatherController.BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RouteControllerIT extends BaseIT {
  @Test
  @Sql(scripts = "classpath:sql/insertForecast.sql")
  @Sql(scripts = "classpath:sql/clearTables.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
      config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  void testCreateRoute_Success() throws Exception {
    mockMvc
        .perform(post(BASE_URL + ROUTE_URL)
            .content(readMockRequest("create-route-request"))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isCreated());

    mockMvc
        .perform(get(BASE_URL + ROUTE_URL + "/test"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("test"))
        .andExpect(jsonPath("cities[0].cityName").value("London"));
  }
}
