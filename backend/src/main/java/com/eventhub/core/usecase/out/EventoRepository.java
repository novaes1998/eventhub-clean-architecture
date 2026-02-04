package com.eventhub.core.usecase.out;

import com.eventhub.core.domain.model.Evento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventoRepository {

    Optional<Evento> buscarPorId(UUID id);

    List<Evento> listar();

    Evento salvar(Evento evento);

    void deletar(UUID id);
}
