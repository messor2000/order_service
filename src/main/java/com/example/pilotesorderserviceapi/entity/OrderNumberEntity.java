package com.example.pilotesorderserviceapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "order_number")
public class OrderNumberEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Integer orderNumber;

  public OrderNumberEntity(Integer orderNumber) {
    this.orderNumber = orderNumber;
  }
}
