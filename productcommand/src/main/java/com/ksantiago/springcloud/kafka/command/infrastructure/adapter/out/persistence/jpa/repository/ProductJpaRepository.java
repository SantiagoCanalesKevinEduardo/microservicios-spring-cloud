package com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.repository;

import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
}
