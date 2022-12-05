package com.example.pilotesorderserviceapi.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private UUID id;
  private Integer orderNumber;
  private String deliveryAddress;
  private Integer pilotesAmount;
  private BigDecimal price;
  private String clientEmail;
  private String createdAt;
  private String clientName;
}
