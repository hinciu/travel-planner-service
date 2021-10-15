package com.test.travelplanner.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.travelplanner.dto.ErrorResponse;
import com.test.travelplanner.exception.ProviderException;
import com.test.travelplanner.exception.ResourceNotFoundException;
import com.test.travelplanner.exception.RouteException;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(final ProviderException ex) {
    val error = ex.getMessage();
    log.error(error);
    return buildResponse(HttpStatus.CONFLICT, error);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(final ResourceNotFoundException ex) {
    val error = ex.getMessage();
    log.error(error);
    return buildResponse(HttpStatus.NOT_FOUND, error);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(final RouteException ex) {
    val error = ex.getMessage();
    log.error(error);
    return buildResponse(UNPROCESSABLE_ENTITY, error);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleInternalError(final Exception ex) {
    return buildResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(final MethodArgumentNotValidException ex) {
    val details = ex.getBindingResult().getFieldErrors().stream()
        .map(e -> e.getField() + ":" + e.getDefaultMessage()).collect(Collectors.toList());
    return buildResponse(UNPROCESSABLE_ENTITY, ex.getMessage(), details);
  }

  private ResponseEntity<ErrorResponse> buildResponse(final HttpStatus status, final String msg) {
    return ResponseEntity.status(status).body(new ErrorResponse(msg));
  }

  private ResponseEntity<ErrorResponse> buildResponse(final HttpStatus status, final String msg,
      final List<String> details) {
    return ResponseEntity.status(status).body(new ErrorResponse(msg, details));
  }
}
