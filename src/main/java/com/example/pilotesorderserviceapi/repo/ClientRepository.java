package com.example.pilotesorderserviceapi.repo;

import com.example.pilotesorderserviceapi.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
