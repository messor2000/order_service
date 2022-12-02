package com.example.pilotesorderserviceapi.entity;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class ClientEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
      flags = Pattern.Flag.CASE_INSENSITIVE)
  private String email;
  private String phoneNumber;
  private String deliveryAddress;
  private Instant createdAt;
}
