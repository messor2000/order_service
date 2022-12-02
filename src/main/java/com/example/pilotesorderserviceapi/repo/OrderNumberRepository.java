package com.example.pilotesorderserviceapi.repo;

import com.example.pilotesorderserviceapi.entity.OrderNumberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNumberRepository extends JpaRepository<OrderNumberEntity, Long> {
  Optional<OrderNumberEntity> getFirst();
}
