package com.example.pilotesorderserviceapi.entity;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long orderNumber;
  private String deliveryAddress;
  private Integer pilotesAmount;
  private BigDecimal price;
  private Instant createdAt;
  private String clientEmail;
}
