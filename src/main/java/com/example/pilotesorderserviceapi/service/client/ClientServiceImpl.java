package com.example.pilotesorderserviceapi.service.client;

import static com.example.pilotesorderserviceapi.exception.NotFoundException.clientNotFoundById;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.entity.ClientEntity;
import com.example.pilotesorderserviceapi.exception.ClientExistsException;
import com.example.pilotesorderserviceapi.repo.ClientRepository;
import com.example.pilotesorderserviceapi.util.ClientMapper;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientMapper clientMapper;
  private final ClientRepository clientRepository;

  @Override
  @Transactional
  public Client createClient(Client client) {
    if (!clientRepository.findByEmail(client.getEmail()).equals(Optional.empty())) {
      throw new ClientExistsException("Client exists with email: " + client.getEmail());
    }
    if (!checkClientData(client.getEmail(), client.getPhoneNumber())) {
      throw new InputMismatchException("Error in email or phone number input");
    }

    client.setCreatedAt(Instant.now());
    ClientEntity entity = clientMapper.convert(client);
    return clientMapper.convert(clientRepository.save(entity));
  }

  @Override
  @Transactional(readOnly = true)
  public Client getClientById(Long clientId) {
    return clientRepository.findById(clientId).map(clientMapper::convert).orElseThrow(() ->
        clientNotFoundById(clientId));
  }

  @Override
  @Transactional
  public void deleteClient(Long clientId) {
    clientRepository.deleteById(clientId);
  }

  private boolean checkClientData(String email, String phoneNumber) {
    return email.toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}") &&
        !phoneNumber.matches("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
  }
}
