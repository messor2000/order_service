package com.example.pilotesorderserviceapi.util;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.entity.ClientEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
  public Client convert(ClientEntity clientEntity) {
    Client client = new Client();
    BeanUtils.copyProperties(clientEntity, client);
    return client;
  }

  public ClientEntity convert(Client client) {
    ClientEntity clientEntity = new ClientEntity();
    BeanUtils.copyProperties(client, clientEntity);
    return clientEntity;
  }
}
