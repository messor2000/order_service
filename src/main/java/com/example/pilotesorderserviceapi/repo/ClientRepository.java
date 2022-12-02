package com.example.pilotesorderserviceapi.repo;

import com.example.pilotesorderserviceapi.entity.ClientEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
  Optional<ClientEntity> findByEmail(String email);
}
