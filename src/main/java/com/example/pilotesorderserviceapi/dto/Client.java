package com.example.pilotesorderserviceapi.dto;

import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Client {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String deliveryAddress;
  private Instant createdAt;

  public Client(Long id, String firstName, String lastName, String email, String phoneNumber, String deliveryAddress) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.deliveryAddress = deliveryAddress;
  }
}
