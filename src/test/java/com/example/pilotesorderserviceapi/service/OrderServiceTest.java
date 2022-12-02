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
import com.example.pilotesorderserviceapi.repo.OrderRepository;
import com.example.pilotesorderserviceapi.service.order.OrderServiceImpl;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
  @Mock
  private OrderRepository orderRepository;
  @InjectMocks
  private OrderServiceImpl orderService;

  private Client client;
  private Order order;

  @BeforeEach
  public void setup(){
    client = new Client(1L, "name", "lastname", "test@gmail.com", "12345",
        "testAddress");
    order = new Order(1L, 1, 5, Instant.now());
  }

  @DisplayName("test for getOrderById method")
  @Test
  public void givenOrderId_whenGetOrderById_thenReturnOrderObject(){
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);

    given(orderRepository.findById(1L)).willReturn(Optional.of(orderEntity));

    Order finedOrder = orderService.findOrderById(order.getId());

    assertThat(finedOrder).isNotNull();
  }

  @DisplayName("test for createOrder method")
  @Test
  public void givenOrderAndClientObjects_whenSaveOrder_thenReturnOrderObject(){
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);

    given(orderRepository.save(orderEntity)).willReturn(orderEntity);

    Order savedOrder = orderService.createOrder(5, client);

    assertThat(savedOrder).isNotNull();
  }

  @DisplayName("test for createOrder method which throw validation error")
  @Test
  public void givenOrderObject_whenSaveOrder_thenThrowsException(){
    order = new Order(1L, 1, 7, Instant.now());
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);

    assertThrows(InputMismatchException.class, () -> orderService.createOrder(6, client));

    verify(orderRepository, never()).save(any(OrderEntity.class));
  }

  @DisplayName("test for getOrderByClientData method")
  @Test
  public void givenClientEmail_whenGetOrderByClientData_thenReturnListOrderObject(){
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);
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
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);

    given(orderRepository.save(orderEntity)).willReturn(orderEntity);
    order.setPilotesAmount(10);
    Order updatedOrder = orderService.updateOrderDetails(1, order);

    assertThat(updatedOrder.getOrderNumber()).isEqualTo(10);
  }

  @DisplayName("test for updateOrderDetails method after 5 min throws an exception")
  @Test
  public void givenOrderNumberAndOrderObject_whenUpdateOrder_thenThrowsException(){
    order = new Order(1L, 1, 7, Instant.now().minusSeconds(500));
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);

    assertThrows(UpdateErrorException.class, () -> orderService.updateOrderDetails(1, order));

    verify(orderRepository, never()).save(any(OrderEntity.class));
  }

  @DisplayName("test for deleteOrder method")
  @Test
  public void givenOrderId_whenDeleteOrder_thenNothing(){
    long orderId = 1L;

    willDoNothing().given(orderRepository).deleteById(orderId);

    orderService.deleteOrder(orderId);

    verify(orderRepository, times(1)).deleteById(orderId);
  }

  @DisplayName("test for getOrders method")
  @Test
  public void givenOrderList_whenGetOrders_thenReturnOrdersList(){
    Order order1 = new Order(1L, 1, 5,Instant.now());

    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);
    OrderEntity orderEntity1 = new OrderEntity();
    BeanUtils.copyProperties(order1, orderEntity1);

    given(orderRepository.findAll()).willReturn(List.of(orderEntity, orderEntity1));

    List<Order> orderList = orderService.getOrders();

    assertThat(orderList).isNotNull();
    assertThat(orderList.size()).isEqualTo(2);
  }

  @DisplayName("test for getOrderByOrderNumber method")
  @Test
  public void givenOrderNumber_whenGetOrderByNumber_thenReturnOrderObject(){
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);

    given(orderRepository.findByOrderNumber(1)).willReturn(Optional.of(orderEntity));

    Order finedOrder = orderService.findOrderByOrderNumber(order.getOrderNumber());

    assertThat(finedOrder).isNotNull();
  }
}
