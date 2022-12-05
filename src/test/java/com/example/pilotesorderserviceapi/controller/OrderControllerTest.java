package com.example.pilotesorderserviceapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.pilotesorderserviceapi.PilotesOrderServiceApiApplication;
import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.dto.Order;
import com.example.pilotesorderserviceapi.exception.ObjectToJsonStringException;
import com.example.pilotesorderserviceapi.service.order.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
public class OrderControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private OrderServiceImpl orderService;
  private Order order;
  private Client client;

  @BeforeEach
  void setUp() {
    String clientEmail = "test@gmail.com";
    String clientName = "test@gmail.com";
    order = Order.builder()
        .id(UUID.randomUUID())
        .orderNumber(1)
        .pilotesAmount(5)
        .clientEmail(clientEmail)
        .clientName(clientName)
        .build();
    client = Client.builder()
        .id(UUID.randomUUID())
        .firstName(clientName)
        .lastName("lastName")
        .email(clientEmail)
        .phoneNumber("(202) 555-0125")
        .deliveryAddress("testAddress")
        .build();
  }

  @Test
  @DisplayName("test get method getOrder")
  public void givenOrderId_whenGetOrder_thenReturnStatusOK() throws Exception {
    when(orderService.getOrderById(order.getId())).thenReturn(order);
    mockMvc.perform(get("/orders/{id}", order.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(order)));
  }

  @Test
  @DisplayName("test post method createOrder")
  public void givenAnAmountOfOrderAndClientObject_whenCreateOrder_thenReturnStatusOK() throws Exception {
    when(orderService.createOrder(eq(5), any(Client.class))).thenAnswer(c -> new Order());
    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .param("pilotesAmount", "5")
            .content(asJsonString(order)))
        .andExpect(status().isOk());
    verify(orderService, times(1)).createOrder(eq(5), any(Client.class));
  }

  @Test
  @DisplayName("test get method findClientOrder")
  public void givenClientEmail_whenGetClientOrder_thenReturnStatusOK() throws Exception {
    when(orderService.getOrdersByClientEmail(client.getEmail())).thenReturn(List.of(order));
    mockMvc.perform(get("/orders/email/{email}", client.getEmail()))
        .andExpect(status().isOk());
    verify(orderService, times(1)).getOrdersByClientEmail(client.getEmail());
  }

  @Test
  @DisplayName("test get method getOrders")
  public void whenGetOrders_thenReturnStatusOK() throws Exception {
    when(orderService.getOrders()).thenReturn(List.of(order));
    mockMvc.perform(get("/orders"))
        .andExpect(status().isOk());
    verify(orderService, times(1)).getOrders();
  }

  @Test
  @DisplayName("test get method findOrderByOrderNumber")
  public void givenOrderNumber_whenGetOrderByNumber_thenReturnStatusOK() throws Exception {
    when(orderService.getOrderByOrderNumber(order.getOrderNumber())).thenReturn(order);
    mockMvc.perform(get("/orders/number/{number}", order.getOrderNumber().toString()))
        .andExpect(status().isOk());
    verify(orderService, times(1)).getOrderByOrderNumber(order.getOrderNumber());
  }

  @Test
  @DisplayName("test post method createOrder")
  public void givenNumberOrderAndClientObject_whenEditOrder_thenReturnStatusOK() throws Exception {
    when(orderService.updateOrderDetails(order.getOrderNumber(), order)).thenAnswer(c -> new Order());
    mockMvc.perform(put("/orders/{orderNumber}/edit", order.getOrderNumber())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(order)))
        .andExpect(status().isOk());
    verify(orderService, times(1)).updateOrderDetails(eq(order.getOrderNumber()), any(Order.class));
  }

  @Test
  @DisplayName("test delete method deleteOrder")
  public void givenOrderId_whenDeleteOrder_thenReturnStatusOK() throws Exception {
    mockMvc.perform(delete("/orders/{id}", order.getId()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("test get method getOrders")
  public void whenGetOrdersByClient_thenReturnStatusOK() throws Exception {
    String clientName = "Name";
    when(orderService.getOrders()).thenReturn(List.of(order));
    mockMvc.perform(get("/orders/client/{clientName}",  clientName))
        .andExpect(status().isOk());
    verify(orderService, times(1)).getOrdersByClientName(clientName);
  }

  private String asJsonString(final Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new ObjectToJsonStringException(e.getMessage());
    }
  }
}
