package com.example.pilotesorderserviceapi.controller;

import com.example.pilotesorderserviceapi.dto.ApiError;
import com.example.pilotesorderserviceapi.exception.ClientExistsException;
import com.example.pilotesorderserviceapi.exception.NotFoundException;
import com.example.pilotesorderserviceapi.exception.ObjectToJsonStringException;
import com.example.pilotesorderserviceapi.exception.UpdateErrorException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value = ClientExistsException.class)
  public ResponseEntity<ApiError> handleClientExistsException(ClientExistsException e) {
    ApiError error = configureApiError(e);
    HttpStatus status = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
    ApiError error = configureApiError(e);
    HttpStatus status = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(value = UpdateErrorException.class)
  public ResponseEntity<ApiError> handleUpdateErrorException(UpdateErrorException e) {
    ApiError error = configureApiError(e);
    HttpStatus status = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(value = ObjectToJsonStringException.class)
  public ResponseEntity<ApiError> handleObjectToJsonStringException(ObjectToJsonStringException e) {
    ApiError error = configureApiError(e);
    HttpStatus status = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(error, status);
  }

  private ApiError configureApiError(Exception e) {
    ApiError error = new ApiError();
    error.setStatus(HttpStatus.BAD_REQUEST);
    error.setTimestamp(LocalDateTime.now());
    error.setException("Exception: " + e.getMessage());

    return error;
  }
}
