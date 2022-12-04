package com.example.pilotesorderserviceapi.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }

  public static NotFoundException clientNotFoundById(UUID clientId) {
    return new NotFoundException("User not found by id: " + clientId);
  }

  public static NotFoundException orderNotFoundByOrderNumber(String orderNumber) {
    return new NotFoundException("Order not found by order number: " + orderNumber);
  }

  public static NotFoundException orderNotFoundByOrderId(UUID orderId) {
    return new NotFoundException("Order not found by order id: " + orderId);
  }

  public static NotFoundException orderNumberNotFound() {
    return new NotFoundException("Order number entity not found");
  }
}
