package com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.repository;

import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ClientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientJpaEntity, Long> {
}
