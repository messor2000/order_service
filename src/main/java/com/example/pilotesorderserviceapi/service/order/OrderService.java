package com.example.pilotesorderserviceapi.service.order;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.dto.Order;
import java.util.List;

public interface OrderService {
  Order findOrderById(Long orderId);
  Order createOrder(Integer pilotesAmount, Client client);
  List<Order> getOrdersByClientData(String clientData);
  Order updateOrderDetails(Integer orderNumber, Order order);
  void deleteOrder(Long orderId);
  List<Order> getOrders();
  Order findOrderByOrderNumber(Integer orderNumber);
}
