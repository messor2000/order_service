package com.example.pilotesorderserviceapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrderNumberEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long orderNumber;
//  @OneToOne(mappedBy = "orderNumber")
//  private OrderEntity order;
}
