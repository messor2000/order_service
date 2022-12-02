package com.example.pilotesorderserviceapi.repo;

import com.example.pilotesorderserviceapi.entity.OrderEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  Optional<OrderEntity> findByOrderNumber(Long orderNumber);
  List<OrderEntity> findByClientEmailContaining(String clientEmail);
}
