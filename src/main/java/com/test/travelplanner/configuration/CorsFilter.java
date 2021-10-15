package com.test.travelplanner.configuration;


import static com.test.travelplanner.controller.WeatherController.BASE_URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsFilter {

  @Value("${cors.filter.allowedOrigins}")
  private String[] allowedOrigins;

  private static final String WILD_CARD = "/**";

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping(BASE_URL + WILD_CARD)
            .allowedOrigins(allowedOrigins).allowedMethods("GET", "PATCH","POST", "DELETE");;
      }
    };
  }
}
