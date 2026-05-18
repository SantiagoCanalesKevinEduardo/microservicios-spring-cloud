package com.ksantiago.springcloud.kafka.command.repositories;

import com.ksantiago.springcloud.kafka.command.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
