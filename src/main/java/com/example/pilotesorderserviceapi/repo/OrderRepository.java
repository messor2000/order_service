package com.example.pilotesorderserviceapi.repo;

import com.example.pilotesorderserviceapi.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
