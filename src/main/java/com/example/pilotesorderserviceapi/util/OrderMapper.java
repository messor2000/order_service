package com.example.pilotesorderserviceapi.util;

import com.example.pilotesorderserviceapi.dto.Order;
import com.example.pilotesorderserviceapi.entity.OrderEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
  public Order convert(OrderEntity orderEntity) {
    Order order = new Order();
    BeanUtils.copyProperties(orderEntity, order);
    return order;
  }

  public OrderEntity convert(Order order) {
    OrderEntity orderEntity = new OrderEntity();
    BeanUtils.copyProperties(order, orderEntity);
    return orderEntity;
  }
}
