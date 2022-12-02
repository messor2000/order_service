package com.example.pilotesorderserviceapi.controller;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.service.client.ClientService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClientController {
  ClientService clientService;

  @ApiOperation(value = "Get Board by ID")
  @GetMapping(path = "/client/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Client> getClient(@PathVariable Long id) {
    Client client = clientService.getClientById(id);
    return ResponseEntity.ok(client);
  }

  @ApiOperation(value = "Create new client")
  @PostMapping(path = "/client",
      consumes = {MediaType.APPLICATION_JSON_VALUE },
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Client> createClient(@RequestBody @Valid Client client) {
    Client newClient = clientService.createClient(client);
    return ResponseEntity.ok(newClient);
  }

  @DeleteMapping(path = "/client/{id}")
  @ApiOperation(value = "Delete client by ID")
  public void deleteClient(@PathVariable Long id) {
    clientService.deleteClient(id);
  }
}
