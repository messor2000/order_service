package com.example.pilotesorderserviceapi.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class ClientEntity {
  @Id
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String deliveryAddress;
  private Instant createdAt;
}
