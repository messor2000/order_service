package com.example.pilotesorderserviceapi.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }

  public static NotFoundException clientNotFoundById(Long clientId) {
    return new NotFoundException("User not found by id: " + clientId);
  }

  public static NotFoundException orderNotFoundByOrderNumber(String orderNumber) {
    return new NotFoundException("Order not found by order number: " + orderNumber);
  }

  public static NotFoundException orderNotFoundByOrderId(Long orderId) {
    return new NotFoundException("Order not found by order id: " + orderId);
  }
}
