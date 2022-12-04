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
import com.example.pilotesorderserviceapi.service.order.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
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
    order = Order.builder()
        .id(UUID.randomUUID())
        .orderNumber(1)
        .pilotesAmount(5)
        .clientEmail(clientEmail)
        .build();
    client = new Client("name", "lastname", "test@gmail.com", "12345",
        "testAddress");
  }

  @Test
  @DisplayName("test get method getOrder")
  public void givenOrderId_whenGetOrder_thenReturnStatusOK() throws Exception {
    when(orderService.getOrderById(order.getId())).thenReturn(order);
    mockMvc.perform(get("/order/{id}", order.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(order)));
  }

  @Test
  @DisplayName("test post method createOrder")
  public void givenAnAmountOfOrderAndClientObject_whenCreateOrder_thenReturnStatusOK() throws Exception {
    when(orderService.createOrder(5, any(Client.class))).thenAnswer(c -> new Order());
    mockMvc.perform(post("/order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(order)))
        .andExpect(status().isOk());
    verify(orderService, times(1)).createOrder(eq(5), any(Client.class));
  }

  @Test
  @DisplayName("test get method findClientOrder")
  public void givenClientEmail_whenGetClientOrder_thenReturnStatusOK() throws Exception {
    when(orderService.getOrdersByClientData(client.getEmail())).thenReturn(List.of(order));
    mockMvc.perform(get("/orders/{email}", client.getEmail()))
        .andExpect(status().isOk());
    verify(orderService, times(1)).getOrdersByClientData(client.getEmail());
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
    mockMvc.perform(get("/order/number/{number}", order.getOrderNumber().toString()))
        .andExpect(status().isOk());
    verify(orderService, times(1)).getOrderByOrderNumber(order.getOrderNumber());
  }

  @Test
  @DisplayName("test post method createOrder")
  public void givenNumberOrderAndClientObject_whenEditOrder_thenReturnStatusOK() throws Exception {
    when(orderService.updateOrderDetails(order.getOrderNumber(), order)).thenAnswer(c -> new Order());
    mockMvc.perform(put("/order/{orderNumber}/edit", order.getOrderNumber())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(order)))
        .andExpect(status().isOk());
    verify(orderService, times(1)).updateOrderDetails(eq(order.getOrderNumber()), any(Order.class));
  }

  @Test
  @DisplayName("test delete method deleteOrder")
  public void givenOrderId_whenDeleteOrder_thenReturnStatusOK() throws Exception {
    mockMvc.perform(delete("/order/{id}", order.getId()))
        .andExpect(status().isOk());
  }

  private String asJsonString(final Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
