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
import com.example.pilotesorderserviceapi.dto.Order;
import com.example.pilotesorderserviceapi.entity.OrderEntity;
import com.example.pilotesorderserviceapi.exception.UpdateErrorException;
import com.example.pilotesorderserviceapi.repo.OrderNumberRepository;
import com.example.pilotesorderserviceapi.repo.OrderRepository;
import com.example.pilotesorderserviceapi.service.order.OrderServiceImpl;
import com.example.pilotesorderserviceapi.util.OrderMapper;
import com.example.pilotesorderserviceapi.util.TimeFormatter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private OrderNumberRepository orderNumberRepository;
  @InjectMocks
  private OrderServiceImpl orderService;
  @Spy
  private OrderMapper orderMapper;
  @Spy
  private TimeFormatter timeFormatter;

  private Client client;
  private Order order;

  @BeforeEach
  public void setup(){
    order = Order.builder()
        .orderNumber(1)
        .deliveryAddress(client.getDeliveryAddress())
        .pilotesAmount(5)
        .createdAt(timeFormatter.formatTime(Instant.now()))
        .price(new BigDecimal("6.65"))
        .clientEmail(client.getEmail())
        .build();
    client = Client.builder()
        .firstName("name")
        .lastName("lastName")
        .email("test@gmail.com")
        .phoneNumber("(202) 555-0125")
        .deliveryAddress("testAddress")
        .createdAt(timeFormatter.formatTime(Instant.now()))
        .build();
  }

  @DisplayName("test for getOrderById method")
  @Test
  public void givenOrderId_whenGetOrderById_thenReturnOrderObject(){
    OrderEntity orderEntity = orderMapper.convert(order);

    given(orderRepository.findById(order.getId())).willReturn(Optional.of(orderEntity));

    Order finedOrder = orderService.getOrderById(order.getId());

    assertThat(finedOrder).isNotNull();
  }

  @DisplayName("test for createOrder method")
  @Test
  public void givenOrderAndClientObjects_whenSaveOrder_thenReturnOrderObject(){
    OrderEntity orderEntity = orderMapper.convert(order);

    given(orderRepository.save(orderEntity)).willReturn(orderEntity);

    Order savedOrder = orderService.createOrder(5, client);

    assertThat(savedOrder).isNotNull();
  }

  @DisplayName("test for createOrder method which throw validation error")
  @Test
  public void givenOrderObject_whenSaveOrder_thenThrowsException(){
    order = Order.builder()
        .id(UUID.randomUUID())
        .orderNumber(1)
        .pilotesAmount(7)
        .build();

    assertThrows(InputMismatchException.class, () -> orderService.createOrder(6, client));

    verify(orderRepository, never()).save(any(OrderEntity.class));
  }

  @DisplayName("test for getOrderByClientData method")
  @Test
  public void givenClientEmail_whenGetOrderByClientData_thenReturnListOrderObject(){
    OrderEntity orderEntity = orderMapper.convert(order);

    given(orderRepository.findByClientEmailContaining(client.getEmail())).willReturn(List.of(orderEntity));

    List<Order> orderList = orderService.getOrdersByClientData(client.getEmail());

    assertThat(orderList).isNotNull();
    assertThat(orderList.size()).isEqualTo(1);
  }

  @DisplayName("test for getOrderByClientData method with invalid input")
  @Test
  public void givenClientEmail_whenGetOrderByClientData_thenThrowsException(){
    String invalidData = "invalid data";
    assertThrows(InputMismatchException.class, () -> orderService.getOrdersByClientData(invalidData));

    verify(orderRepository, never()).findByClientEmailContaining(invalidData);
  }

  @DisplayName("test for updateOrderDetails method")
  @Test
  public void givenOrderNumberAndOrderObject_whenUpdateOrder_thenReturnUpdatedOrder(){
    orderRepository.save(orderMapper.convert(order));
    OrderEntity orderEntity = orderMapper.convert(order);

    given(orderRepository.save(orderEntity)).willReturn(orderEntity);
    order.setPilotesAmount(10);
    order.setCreatedAt(timeFormatter.formatTime(Instant.now()));
    Order updatedOrder = orderService.updateOrderDetails(1, order);

    assertThat(updatedOrder.getOrderNumber()).isEqualTo(10);
  }

  @DisplayName("test for updateOrderDetails method after 5 min throws an exception")
  @Test
  public void givenOrderNumberAndOrderObject_whenUpdateOrder_thenThrowsException(){
    order = Order.builder()
        .id(UUID.randomUUID())
        .orderNumber(1)
        .pilotesAmount(5)
        .createdAt(timeFormatter.formatTime(Instant.now().minusSeconds(500)))
        .build();

    assertThrows(UpdateErrorException.class, () -> orderService.updateOrderDetails(order.getOrderNumber(), order));

    verify(orderRepository, never()).save(any(OrderEntity.class));
  }

  @DisplayName("test for deleteOrder method")
  @Test
  public void givenOrderId_whenDeleteOrder_thenNothing(){
    willDoNothing().given(orderRepository).deleteById(order.getId());

    orderService.deleteOrder(order.getId());

    verify(orderRepository, times(1)).deleteById(order.getId());
  }

  @DisplayName("test for getOrders method")
  @Test
  public void givenOrderList_whenGetOrders_thenReturnOrdersList(){
    Order order1 = Order.builder()
        .id(UUID.randomUUID())
        .orderNumber(1)
        .pilotesAmount(5)
        .createdAt(timeFormatter.formatTime(Instant.now()))
        .build();

    OrderEntity orderEntity = orderMapper.convert(order);
    OrderEntity orderEntity1 = orderMapper.convert(order1);

    given(orderRepository.findAll()).willReturn(List.of(orderEntity, orderEntity1));

    List<Order> orderList = orderService.getOrders();

    assertThat(orderList).isNotNull();
    assertThat(orderList.size()).isEqualTo(2);
  }

  @DisplayName("test for getOrderByOrderNumber method")
  @Test
  public void givenOrderNumber_whenGetOrderByNumber_thenReturnOrderObject(){
    OrderEntity orderEntity = orderMapper.convert(order);

    given(orderRepository.findByOrderNumber(order.getOrderNumber())).willReturn(Optional.of(orderEntity));

    Order finedOrder = orderService.getOrderByOrderNumber(order.getOrderNumber());

    assertThat(finedOrder).isNotNull();
  }
}
