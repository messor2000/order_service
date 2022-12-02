package com.example.pilotesorderserviceapi.service.client;

import com.example.pilotesorderserviceapi.dto.Client;
import java.util.List;

public interface ClientService {
  Client createClient(Client client);
  Client getClientById(Long clientId);
  void deleteClient(Long clientId);
}
