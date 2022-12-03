package com.example.pilotesorderserviceapi.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
  private UUID id;
  private Integer orderNumber;
  private String deliveryAddress;
  private Integer pilotesAmount;
  private BigDecimal price;
  private String clientEmail;
  private Instant createdAt;

  public Order(UUID id, Integer orderNumber, Integer pilotesAmount, Instant createdAt) {
    this.id = id;
    this.orderNumber = orderNumber;
    this.pilotesAmount = pilotesAmount;
    this.createdAt = createdAt;
  }

  public Order(UUID id, Integer orderNumber, Integer pilotesAmount, Instant createdAt, String clientEmail) {
    this.id = id;
    this.orderNumber = orderNumber;
    this.pilotesAmount = pilotesAmount;
    this.createdAt = createdAt;
    this.clientEmail = clientEmail;
  }
}
