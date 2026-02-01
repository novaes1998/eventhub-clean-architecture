package com.eventhub.infra.persistence.adapter;

import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.usecase.out.EventoRepository;
import com.eventhub.infra.persistence.entity.EventoEntity;
import com.eventhub.infra.persistence.repository.EventoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EventoRepositoryAdapter implements EventoRepository {

    private final EventoJpaRepository jpaRepository;

    public EventoRepositoryAdapter(EventoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Evento> buscarPorId(UUID id) {

        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Evento> listar() {

        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Evento salvar(Evento evento) {

        EventoEntity entity = toEntity(evento);
        EventoEntity salvo = jpaRepository.save(entity);

        return toDomain(salvo);
    }

    @Override
    public void deletar(UUID id) {

        jpaRepository.deleteById(id);
    }

    // ---------------Mappers---------------

    private EventoEntity toEntity(Evento evento){

        return new EventoEntity(
                evento.getId(),
                evento.getNome(),
                evento.getData(),
                evento.getLocal(),
                evento.getCapacidade()
        );
    }

    private Evento toDomain(EventoEntity entity){

        return new Evento(
                entity.getId(),
                entity.getNome(),
                entity.getData(),
                entity.getLocal(),
                entity.getCapacidade()
        );
    }
}
