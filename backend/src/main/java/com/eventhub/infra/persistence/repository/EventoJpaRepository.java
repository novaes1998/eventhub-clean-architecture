package com.eventhub.infra.persistence.repository;

import com.eventhub.infra.persistence.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventoJpaRepository extends JpaRepository<EventoEntity, UUID> {
}
