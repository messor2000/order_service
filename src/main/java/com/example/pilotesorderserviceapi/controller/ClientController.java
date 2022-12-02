package com.example.pilotesorderserviceapi.controller;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.service.client.ClientService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

  @GetMapping(path = "/client/{id}")
  @ApiOperation(value = "Get Board by ID")
  public ResponseEntity<Client> getClient(@PathVariable Long id) {
    Client client = clientService.getClientById(id);
    return ResponseEntity.ok(client);
  }

  @PostMapping(path = "/client")
  @ApiOperation(value = "Create new client")
  public ResponseEntity<Client> createBoard(@RequestBody @Valid Client client) {
    Client newClient = clientService.createClient(client);
    return ResponseEntity.ok(newClient);
  }

  @DeleteMapping(path = "/client/{id}")
  @ApiOperation(value = "Delete client by ID")
  public void deleteClient(@PathVariable Long id) {
    clientService.deleteClient(id);
  }
}
