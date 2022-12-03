package com.example.pilotesorderserviceapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.entity.ClientEntity;
import com.example.pilotesorderserviceapi.exception.ClientExistsException;
import com.example.pilotesorderserviceapi.repo.ClientRepository;
import com.example.pilotesorderserviceapi.service.client.ClientServiceImpl;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
  @InjectMocks
  private ClientServiceImpl clientService;
  @Mock
  private ClientRepository clientRepository;
  private Client client;

  @BeforeEach
  public void setup(){
    client = new Client(UUID.randomUUID(), "name", "lastname", "test@gmail.com", "12345",
        "testAddress");
  }

  @DisplayName("test for createClient method")
  @Test
  public void givenClientObject_whenSaveClient_thenReturnClientObject(){
    given(clientRepository.findByEmail(client.getEmail()))
        .willReturn(Optional.empty());

    ClientEntity clientEntity = new ClientEntity();
    BeanUtils.copyProperties(client, clientEntity);

    given(clientRepository.save(clientEntity)).willReturn(clientEntity);

    Client savedClient = clientService.createClient(client);

    assertThat(savedClient).isNotNull();
  }

  @DisplayName("test for createClient method which throw validation error")
  @Test
  public void givenClientObject_whenSaveClient_thenThrowsException(){
    client = new Client(UUID.randomUUID(), "name", "lastname", "incorrect email", "12345",
        "testAddress");
    ClientEntity clientEntity = new ClientEntity();
    BeanUtils.copyProperties(client, clientEntity);

    given(clientRepository.findByEmail(client.getEmail()))
        .willReturn(Optional.empty());

    assertThrows(InputMismatchException.class, () -> clientService.createClient(client));

    verify(clientRepository, never()).save(any(ClientEntity.class));
  }

  @DisplayName("test for createClient method which throws exception")
  @Test
  public void givenExistingEmail_whenSaveClient_thenThrowsException(){
    ClientEntity clientEntity = new ClientEntity();
    BeanUtils.copyProperties(client, clientEntity);

    given(clientRepository.findByEmail(client.getEmail()))
        .willReturn(Optional.of(clientEntity));

    assertThrows(ClientExistsException.class, () -> clientService.createClient(client));

    verify(clientRepository, never()).save(any(ClientEntity.class));
  }

  @DisplayName("test for getClientById method")
  @Test
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
    ClientEntity clientEntity = new ClientEntity();
    BeanUtils.copyProperties(client, clientEntity);

    given(clientRepository.findById(client.getId())).willReturn(Optional.of(clientEntity));

    Client foundClient = clientService.getClientById(client.getId());

    assertThat(foundClient).isNotNull();
  }

  @DisplayName("test for deleteClient method")
  @Test
  public void givenClientId_whenDeleteClient_thenNothing(){
    willDoNothing().given(clientRepository).deleteById(client.getId());

    clientService.deleteClient(client.getId());

    verify(clientRepository, times(1)).deleteById(client.getId());
  }
}
