package com.example.pilotesorderserviceapi.service.order;

import static com.example.pilotesorderserviceapi.exception.NotFoundException.orderNotFoundByOrderId;
import static com.example.pilotesorderserviceapi.exception.NotFoundException.orderNotFoundByOrderNumber;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.dto.Order;
import com.example.pilotesorderserviceapi.entity.OrderEntity;
import com.example.pilotesorderserviceapi.entity.OrderNumberEntity;
import com.example.pilotesorderserviceapi.exception.UpdateErrorException;
import com.example.pilotesorderserviceapi.repo.OrderNumberRepository;
import com.example.pilotesorderserviceapi.repo.OrderRepository;
import com.example.pilotesorderserviceapi.util.OrderMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
 import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderMapper orderMapper;
  private final OrderRepository orderRepository;
  private final OrderNumberRepository orderNumberRepository;

  @Override
  @Transactional(readOnly = true)
  public Order findOrderById(Long orderId) {
    return orderRepository.findById(orderId).map(orderMapper::convert).orElseThrow(() ->
        orderNotFoundByOrderId(orderId));
  }

  @Override
  @Transactional
  public Order createOrder(Integer pilotesAmount, Client client) {
    if (!checkPilotesAmount(pilotesAmount)) {
      throw new InputMismatchException("You can order only 5, 10, or 15 pilotes, not: " + pilotesAmount);
    }

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setCreatedAt(Instant.now());
    orderEntity.setPilotesAmount(pilotesAmount);
    orderEntity.setDeliveryAddress(client.getDeliveryAddress());
    orderEntity.setPrice(BigDecimal.valueOf(1.33 * pilotesAmount));
    orderEntity.setClientEmail(client.getEmail());
    OrderNumberEntity orderNumber = orderNumberRepository.getFirst().orElse(new OrderNumberEntity());
    orderEntity.setOrderNumber(pilotesAmount);

    orderNumber.setOrderNumber(orderNumber.getOrderNumber() + 1);
    orderNumberRepository.save(orderNumber);
    return orderMapper.convert(orderRepository.save(orderEntity));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> getOrdersByClientData(String clientData) {
    if (!checkInputData(clientData)) {
      throw new InputMismatchException("You can find any orders using this email: " + clientData);
    }

    List<Order> orders = orderRepository.findByClientEmailContaining(clientData).stream()
        .map(orderMapper::convert).toList();

    if (orders.isEmpty()) {
      return Collections.emptyList();
    }

    return orders;
  }

  @Override
  @Transactional
  public Order updateOrderDetails(Integer orderNumber, Order order) {
    Instant now = Instant.now().minusSeconds(300);
    if (order.getCreatedAt().isBefore(now)) {
      throw new UpdateErrorException("You cannot modify order after 5 minutes of it creation");
    }

    OrderEntity foundOrder = orderMapper.convert(findOrderByOrderNumber(orderNumber));
    foundOrder.setDeliveryAddress(order.getDeliveryAddress());
    foundOrder.setCreatedAt(Instant.now());
    if (!foundOrder.getPilotesAmount().equals(order.getPilotesAmount())) {
      if (checkPilotesAmount(order.getPilotesAmount())) {
        throw new InputMismatchException("You can order only 5, 10, or 15 pilotes, not: " + order.getPilotesAmount());
      }
      foundOrder.setPilotesAmount(order.getPilotesAmount());
      foundOrder.setPrice(BigDecimal.valueOf(1.33 * order.getOrderNumber()));
    }

    return orderMapper.convert(orderRepository.save(foundOrder));
  }

  @Override
  @Transactional
  public void deleteOrder(Long orderId) {
    orderRepository.deleteById(orderId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> getOrders() {
    return orderRepository.findAll().stream()
        .map(orderMapper::convert).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Order findOrderByOrderNumber(Integer orderNumber) {
    return orderRepository.findByOrderNumber(orderNumber).map(orderMapper::convert)
        .orElseThrow(() -> orderNotFoundByOrderNumber(String.valueOf(orderNumber)));
  }

  private boolean checkPilotesAmount(Integer pilotesAmount) {
    return pilotesAmount.equals(5) || pilotesAmount.equals(10) || pilotesAmount.equals(15);
  }

  private boolean checkInputData(String clientEmail) {
    return clientEmail.toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}");
  }
}
