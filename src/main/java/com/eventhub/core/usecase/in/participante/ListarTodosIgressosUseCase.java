package com.eventhub.core.usecase.in.participante;

import com.eventhub.core.usecase.dto.participante.CompraIngressoResponse;

import java.util.List;

public interface ListarTodosIgressosUseCase {

    List<CompraIngressoResponse> executar();
}
