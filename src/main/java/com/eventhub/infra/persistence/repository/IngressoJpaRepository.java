package com.eventhub.infra.persistence.repository;

import com.eventhub.infra.persistence.entity.IngressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IngressoJpaRepository extends JpaRepository<IngressoEntity, UUID> {

    List<IngressoEntity> findIngressoByParticipanteId(UUID participanteId);
}
