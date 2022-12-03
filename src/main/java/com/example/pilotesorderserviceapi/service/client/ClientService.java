package com.example.pilotesorderserviceapi.service.client;

import com.example.pilotesorderserviceapi.dto.Client;
import java.util.UUID;

public interface ClientService {
  Client createClient(Client client);
  Client getClientById(UUID clientId);
  void deleteClient(UUID clientId);
}
