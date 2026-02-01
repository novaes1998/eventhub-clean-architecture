package com.eventhub.infra.persistence.adapter;

import com.eventhub.core.domain.model.Ingresso;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.infra.persistence.entity.IngressoEntity;
import com.eventhub.infra.persistence.repository.IngressoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class IngressoRepositoryAdapter implements IngressoRepository {

    private final IngressoJpaRepository jpaRepository;

    public IngressoRepositoryAdapter(IngressoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Ingresso salvar(Ingresso ingresso) {

        IngressoEntity entity = toEntity(ingresso);
        IngressoEntity save = jpaRepository.save(entity);

        return toDomain(save);
    }

    @Override
    public List<Ingresso> listarPorParticipante(UUID participanteId) {

        List<IngressoEntity> entity = jpaRepository.findIngressoByParticipanteId(participanteId);

        return entity.
                stream().
                map(this::toDomain)
                .toList();
    }

    // ---------------Mappers---------------
    private Ingresso toDomain(IngressoEntity entity){

        return new Ingresso(
                entity.getId(),
                entity.getParticipanteId(),
                entity.getEventoId(),
                entity.getDataCompra()
        );
    }

    private IngressoEntity toEntity(Ingresso ingresso){

        return new IngressoEntity(
                ingresso.getId(),
                ingresso.getParticipanteId(),
                ingresso.getEventoId(),
                ingresso.getDataCompra()
        );
    }
}
