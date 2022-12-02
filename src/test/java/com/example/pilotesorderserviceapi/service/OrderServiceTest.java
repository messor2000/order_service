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
import com.example.pilotesorderserviceapi.repo.OrderRepository;
import com.example.pilotesorderserviceapi.service.order.OrderServiceImpl;
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
  private String clientEmail;

  @BeforeEach
  public void setup(){
    clientEmail = "test@gmail.com";
    client = new Client(1L, "name", "lastname", clientEmail, "12345",
        "testAddress");
    order = new Order(1L, 5);
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

    Order savedOrder = orderService.createOrder(order, client);

    assertThat(savedOrder).isNotNull();
  }

  @DisplayName("test for createOrder method which throw validation error")
  @Test
  public void givenOrderObject_whenSaveOrder_thenThrowsException(){
    order = new Order(1L, 7);
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);

    assertThrows(InputMismatchException.class, () -> orderService.createOrder(order, client));

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
}
