package com.example.pilotesorderserviceapi.service.client;

import static com.example.pilotesorderserviceapi.exception.NotFoundException.clientNotFoundById;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.entity.ClientEntity;
import com.example.pilotesorderserviceapi.exception.ClientExistsException;
import com.example.pilotesorderserviceapi.repo.ClientRepository;
import com.example.pilotesorderserviceapi.util.ClientMapper;
import com.example.pilotesorderserviceapi.util.TimeFormatter;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientMapper clientMapper;
  private final ClientRepository clientRepository;
  private final TimeFormatter timeFormatter;

  @Override
  @Transactional
  public Client createClient(Client client) {
    if (!clientRepository.findByEmail(client.getEmail()).equals(Optional.empty())) {
      throw new ClientExistsException("Client exists with email: " + client.getEmail());
    }
    if (!checkClientData(client.getEmail(), client.getPhoneNumber())) {
      throw new InputMismatchException("Error in email or phone number input");
    }
    client.setCreatedAt(timeFormatter.formatTime(Instant.now()));
    ClientEntity entity = clientMapper.convert(client);
    return clientMapper.convert(clientRepository.save(entity));
  }

  @Override
  @Transactional(readOnly = true)
  public Client getClientById(UUID clientId) {
    return clientRepository.findById(clientId).map(clientMapper::convert).orElseThrow(() ->
        clientNotFoundById(clientId));
  }

  @Override
  @Transactional
  public void deleteClient(UUID clientId) {
    clientRepository.deleteById(clientId);
  }

  private boolean checkClientData(String email, String phoneNumber) {
    return email.toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}") &&
        phoneNumber.matches("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
  }
}
