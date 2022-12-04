package com.example.pilotesorderserviceapi.repo;

import com.example.pilotesorderserviceapi.entity.OrderNumberEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNumberRepository extends JpaRepository<OrderNumberEntity, UUID> {
  @Query("select o from OrderNumberEntity o")
  List<OrderNumberEntity> getOrderNumberEntities();
}
