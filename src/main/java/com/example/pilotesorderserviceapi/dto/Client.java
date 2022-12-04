package com.example.pilotesorderserviceapi.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String deliveryAddress;
  private String createdAt;

  public Client(String firstName, String lastName, String email, String phoneNumber, String deliveryAddress,
                String createdAt) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.deliveryAddress = deliveryAddress;
    this.createdAt = createdAt;
  }

  public Client(String firstName, String lastName, String email, String phoneNumber, String deliveryAddress) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.deliveryAddress = deliveryAddress;
  }

  public Client(UUID id, String firstName, String lastName, String email, String phoneNumber, String deliveryAddress) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.deliveryAddress = deliveryAddress;
  }
}
