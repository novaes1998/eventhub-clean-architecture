package com.eventhub.core.usecase.in.participante;

import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;

import java.util.List;
import java.util.UUID;

public interface ListarTodosIgressosUseCase {

    List<CompraIngressoResponse> executar(UUID participante);
}
