package com.example.pilotesorderserviceapi.service.order;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.dto.Order;
import java.util.List;
import java.util.UUID;

public interface OrderService {
  Order getOrderById(UUID orderId);
  Order createOrder(Integer pilotesAmount, Client client);
  List<Order> getOrdersByClientEmail(String clientData);
  Order updateOrderDetails(Integer orderNumber, Order order);
  void deleteOrder(UUID orderId);
  List<Order> getOrders();
  Order getOrderByOrderNumber(Integer orderNumber);
  List<Order> getOrdersByClientName(String client);
}
