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
import com.example.pilotesorderserviceapi.util.TimeFormatter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderMapper orderMapper;
  private final OrderRepository orderRepository;
  private final OrderNumberRepository orderNumberRepository;
  private final TimeFormatter timeFormatter;


  @Override
  @Transactional(readOnly = true)
  public Order getOrderById(UUID orderId) {
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
    orderEntity.setCreatedAt(timeFormatter.formatTime(Instant.now()));
    orderEntity.setPilotesAmount(pilotesAmount);
    orderEntity.setDeliveryAddress(client.getDeliveryAddress());
    orderEntity.setPrice(BigDecimal.valueOf(1.33 * pilotesAmount));
    orderEntity.setClientEmail(client.getEmail());
    orderEntity.setClientName(client.getFirstName());

    orderEntity.setOrderNumber(getOrderNumber());

    return orderMapper.convert(orderRepository.save(orderEntity));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> getOrdersByClientEmail(String clientData) {
    if (!checkInputData(clientData)) {
      throw new InputMismatchException("You can find any orders using this email: " + clientData);
    }

    List<Order> orders = orderRepository.findByClientEmail(clientData).stream()
        .map(orderMapper::convert).collect(Collectors.toList());

    if (orders.isEmpty()) {
      return Collections.emptyList();
    }

    return orders;
  }

  @Override
  @Transactional
  public Order updateOrderDetails(Integer orderNumber, Order order) {
    OrderEntity foundOrder = orderMapper.convert(getOrderByOrderNumber(orderNumber));
    if (!checkOrderTime(foundOrder)) {
      throw new UpdateErrorException("You cannot modify order after 5 minutes of it creation");
    }

    foundOrder.setId(order.getId());
    foundOrder.setDeliveryAddress(order.getDeliveryAddress());
    foundOrder.setCreatedAt(timeFormatter.formatTime(Instant.now()));
    if (!foundOrder.getPilotesAmount().equals(order.getPilotesAmount())) {
      if (checkPilotesAmount(order.getPilotesAmount())) {
        throw new InputMismatchException("You can order only 5, 10, or 15 pilotes, not: " + order.getPilotesAmount());
      }
      foundOrder.setPilotesAmount(order.getPilotesAmount());
      foundOrder.setPrice(BigDecimal.valueOf(1.33 * order.getPilotesAmount()));
    }
    OrderEntity orderEntity = orderRepository.save(foundOrder);

    return orderMapper.convert(orderEntity);
//    return orderMapper.convert(orderRepository.save(foundOrder));
  }

  @Override
  @Transactional
  public void deleteOrder(UUID orderId) {
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
  public Order getOrderByOrderNumber(Integer orderNumber) {
    return orderRepository.findByOrderNumber(orderNumber).map(orderMapper::convert)
        .orElseThrow(() -> orderNotFoundByOrderNumber(String.valueOf(orderNumber)));
  }

  @Override
  public List<Order> getOrdersByClientName(String clientName) {
    return orderRepository.findByClientNameContains(clientName).stream()
        .map(orderMapper::convert).collect(Collectors.toList());
  }

  @Transactional
  public Integer getOrderNumber() {
    List<OrderNumberEntity> orderNumberEntities = orderNumberRepository.getOrderNumberEntities();
    OrderNumberEntity foundOrderNumber = orderNumberEntities.stream().findFirst()
        .orElse(new OrderNumberEntity(0));
    incrementOrderNumber(foundOrderNumber);

    return foundOrderNumber.getOrderNumber();
  }

  @Transactional
  public void incrementOrderNumber(OrderNumberEntity orderNumberEntity) {
    orderNumberEntity.setOrderNumber(orderNumberEntity.getOrderNumber() + 1);
    orderNumberRepository.save(orderNumberEntity);
  }

  private boolean checkPilotesAmount(Integer pilotesAmount) {
    return pilotesAmount.toString().matches("[5, 10, 15]");
//    return pilotesAmount.equals(5) || pilotesAmount.equals(10) || pilotesAmount.equals(15);
  }

  private boolean checkInputData(String clientEmail) {
    return clientEmail.toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}");
  }

  private boolean checkOrderTime(OrderEntity order) {
    String orderTime = order.getCreatedAt();
    int orderMinutes = Integer.parseInt(StringUtils.right(orderTime, 2));
    String now = timeFormatter.formatTime(Instant.now());
    int nowMinutes = Integer.parseInt(StringUtils.right(now, 2));

    return (nowMinutes - orderMinutes) < 5;
  }
}
