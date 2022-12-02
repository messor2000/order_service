package com.example.pilotesorderserviceapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.pilotesorderserviceapi.PilotesOrderServiceApiApplication;
import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.service.client.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = PilotesOrderServiceApiApplication.class)
public class ClientControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private ClientServiceImpl clientService;
  private Client client;

  @BeforeEach
  void setUp() {
    client = new Client(1L, "name", "lastname", "test@gmail.com", "12345",
        "testAddress");
  }

  @Test
  @DisplayName("test post method createClient")
  public void givenClientObject_whenCreateClient_thenReturnStatusOK() throws Exception {
    when(clientService.createClient(any(Client.class))).thenAnswer(c -> new Client());
    mockMvc.perform(post("/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(client)))
        .andExpect(status().isOk());
    verify(clientService, times(1)).createClient(any(Client.class));
  }

  private String asJsonString(final Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}


