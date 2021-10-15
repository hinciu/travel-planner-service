package com.test.travelplanner.exception;

import lombok.Getter;

@Getter
public class ProviderException extends RuntimeException {
  public ProviderException(final String message) {
    super(message);
  }
}
