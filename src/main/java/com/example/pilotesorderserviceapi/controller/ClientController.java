package com.example.pilotesorderserviceapi.controller;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.service.client.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Api(value = "Client Rest Controller")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClientController {
  ClientService clientService;

  @ApiOperation(value = "Get Client by ID")
  @GetMapping(path = "/clients/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Client> getClient(@PathVariable UUID id) {
    Client client = clientService.getClientById(id);
    return ResponseEntity.ok(client);
  }

  @ApiOperation(value = "Create new client")
  @PostMapping(path = "/clients",
      consumes = {MediaType.APPLICATION_JSON_VALUE },
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Client> createClient(@RequestBody Client client) {
    Client newClient = clientService.createClient(client);
    return ResponseEntity.ok(newClient);
  }

  @DeleteMapping(path = "/clients/{id}")
  @ApiOperation(value = "Delete client by ID")
  public void deleteClient(@PathVariable UUID id) {
    clientService.deleteClient(id);
  }
}
