package com.eventhub.core.usecase.out;

import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.domain.model.Participante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipanteRepository {

    Participante salvar(Participante participante);

    Optional<Participante> buscarPorId(UUID id);

    List<Participante> listar();

    void deletar(UUID id);
}
