package com.eventhub.core.usecase.out;

import com.eventhub.core.domain.model.Ingresso;

import java.util.List;
import java.util.UUID;

public interface IngressoRepository {

    Ingresso salvar(Ingresso ingresso);

    List<Ingresso> listarPorParticipante(UUID participanteId);
}
