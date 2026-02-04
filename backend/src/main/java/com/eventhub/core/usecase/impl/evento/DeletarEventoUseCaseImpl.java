package com.eventhub.core.usecase.impl.evento;

import com.eventhub.core.domain.exception.EventoNaoEncontradoException;
import com.eventhub.core.domain.exception.EventoNaoPodeSerDeletadoException;
import com.eventhub.core.domain.model.Evento;
import com.eventhub.core.domain.model.Ingresso;
import com.eventhub.core.usecase.in.evento.DeletarEventoUseCase;
import com.eventhub.core.usecase.out.EventoRepository;
import com.eventhub.core.usecase.out.IngressoRepository;
import com.eventhub.core.usecase.out.ParticipanteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DeletarEventoUseCaseImpl implements DeletarEventoUseCase {

    private final EventoRepository eventoRepository;
    private final IngressoRepository ingressoRepository;

    public DeletarEventoUseCaseImpl(
            EventoRepository eventoRepository,
            IngressoRepository ingressoRepository
    ) {
        this.eventoRepository = eventoRepository;
        this.ingressoRepository = ingressoRepository;
    }

    @Override
    public void executar(UUID id) {

        //1. valida se o evento existe
        Evento eventoSeraExcluido = eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNaoEncontradoException(id));

        //2. verifica se existe algum participante vinculado a este evento a ser deletado
        List<Ingresso> participantesVinculadosAoEvento =
                ingressoRepository.listarPorEvento(id);

        //3. caso exista alguma relação
        if(!participantesVinculadosAoEvento.isEmpty()){

            throw new EventoNaoPodeSerDeletadoException();
        }

        //4. caso não exista relação, deleta evento
        eventoRepository.deletar(id);
    }
}
