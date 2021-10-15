package com.test.travelplanner.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(final String message) {
    super(message);
  }
}
