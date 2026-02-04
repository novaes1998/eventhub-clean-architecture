package com.eventhub.infra.persistence.adapter;

import com.eventhub.core.domain.model.Participante;
import com.eventhub.core.usecase.out.ParticipanteRepository;
import com.eventhub.infra.persistence.entity.ParticipanteEntity;
import com.eventhub.infra.persistence.repository.ParticipanteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ParticipanteRepositoryAdapter implements ParticipanteRepository {

    private final ParticipanteJpaRepository jpaRepository;

    public ParticipanteRepositoryAdapter(ParticipanteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Participante salvar(Participante participante) {

        ParticipanteEntity entity = toEntity(participante);
        ParticipanteEntity salvo = jpaRepository.save(entity);

        return toDomain(salvo);
    }

    @Override
    public Optional<Participante> buscarPorId(UUID id) {

        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Participante> listar() {

        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deletar(UUID id) {

        jpaRepository.deleteById(id);
    }

    // ---------------Mappers---------------

    private ParticipanteEntity toEntity(Participante participante){

        return new ParticipanteEntity(
                participante.getId(),
                participante.getNome(),
                participante.getEmail()
        );
    }

    private Participante toDomain(ParticipanteEntity entity){

        return new Participante(
                entity.getId(),
                entity.getNome(),
                entity.getEmail()
        );
    }
}
