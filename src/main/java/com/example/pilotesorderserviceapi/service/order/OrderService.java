package com.example.pilotesorderserviceapi.service.order;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.dto.Order;
import java.util.List;

public interface OrderService {
  Order findOrderById(Long orderId);
  Order createOrder(Order order, Client client);
  List<Order> getOrdersByClientData(String clientData);
  Order updateOrderDetails(Long orderNumber, Order order);
  void deleteOrder(Long orderId);
  List<Order> getOrders();
  Order findOrderByOrderNumber(Long orderNumber);
}
