package com.example.pilotesorderserviceapi.controller;

import com.example.pilotesorderserviceapi.dto.Client;
import com.example.pilotesorderserviceapi.dto.Order;
import com.example.pilotesorderserviceapi.service.order.OrderService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {
  OrderService orderService;

  @ApiOperation(value = "Get Order by ID")
  @GetMapping(path = "/order/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Order> getOrder(@PathVariable Long id) {
    Order order = orderService.findOrderById(id);
    return ResponseEntity.ok(order);
  }

  @ApiOperation(value = "Create new order")
  @PostMapping(path = "/order",
      consumes = {MediaType.APPLICATION_JSON_VALUE },
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Order> createOrder(@RequestParam(value = "pilotesAmount") Integer pilotesAmount,
                          @RequestBody @Valid Client client) {
    Order order = orderService.createOrder(pilotesAmount, client);
    return ResponseEntity.ok(order);
  }

  @ApiOperation(value = "Find order by client email")
  @GetMapping(path = "/order/{email}",
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<List<Order>> findClientOrder(@PathVariable String email) {
    List<Order> orders = orderService.getOrdersByClientData(email);
    return ResponseEntity.ok(orders);
  }

  @ApiOperation(value = "Edit order")
  @PutMapping(path = "/order/{orderNumber}/edit",
      consumes = {MediaType.APPLICATION_JSON_VALUE },
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Order> editOrder(@PathVariable Integer orderNumber, @RequestBody Order order) {
    Order editedOrder = orderService.updateOrderDetails(orderNumber, order);
    return ResponseEntity.ok(editedOrder);
  }

  @ApiOperation(value = "Delete order")
  @DeleteMapping(path = "/order/{id}")
  public void deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
  }

  @ApiOperation(value = "Find all order")
  @GetMapping(path = "/order",
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<List<Order>> getOrders() {
    List<Order> orders = orderService.getOrders();
    return ResponseEntity.ok(orders);
  }

  @ApiOperation(value = "Get Order by order number")
  @GetMapping(path = "/order/{number}",
      produces = {MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Order> getOrderByOrderNumber(@PathVariable Integer number) {
    Order order = orderService.findOrderByOrderNumber(number);
    return ResponseEntity.ok(order);
  }
}
