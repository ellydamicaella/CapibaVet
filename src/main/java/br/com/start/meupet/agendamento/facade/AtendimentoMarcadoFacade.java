package br.com.start.meupet.agendamento.facade;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoRequestDTO;
import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoStatusDTO;
import br.com.start.meupet.agendamento.usecase.AtendimentoMarcado.AdicionarAtendimentoMarcadoUseCase;
import br.com.start.meupet.agendamento.usecase.AtendimentoMarcado.AtualizaStatusDoAtendimentoMarcadoUseCase;
import br.com.start.meupet.agendamento.usecase.AtendimentoMarcado.ListaAtendimentoMarcadoUserIdUseCase;
import br.com.start.meupet.agendamento.usecase.AtendimentoMarcado.ListaTodosAtendimentosMarcadoUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AtendimentoMarcadoFacade {

    private final ListaTodosAtendimentosMarcadoUseCase listaTodosAtendimentosMarcadoUseCase;
    private final ListaAtendimentoMarcadoUserIdUseCase listaAtendimentoMarcadoUserIdUseCase;
    private final AdicionarAtendimentoMarcadoUseCase adicionarAtendimentoMarcadoUseCase;
    private final AtualizaStatusDoAtendimentoMarcadoUseCase atualizaStatusDoAtendimentoMarcadoUseCase;

    public AtendimentoMarcadoFacade(
            ListaTodosAtendimentosMarcadoUseCase listaTodosAtendimentosMarcadoUseCase,
            ListaAtendimentoMarcadoUserIdUseCase listaAtendimentoMarcadoUserIdUseCase,
            AdicionarAtendimentoMarcadoUseCase adicionarAtendimentoMarcadoUseCase,
            AtualizaStatusDoAtendimentoMarcadoUseCase atualizaStatusDoAtendimentoMarcadoUseCase
    ) {
        this.listaTodosAtendimentosMarcadoUseCase = listaTodosAtendimentosMarcadoUseCase;
        this.listaAtendimentoMarcadoUserIdUseCase = listaAtendimentoMarcadoUserIdUseCase;
        this.adicionarAtendimentoMarcadoUseCase = adicionarAtendimentoMarcadoUseCase;
        this.atualizaStatusDoAtendimentoMarcadoUseCase = atualizaStatusDoAtendimentoMarcadoUseCase;
    }

    public List<AtendimentoMarcadoDTO> listaTodosAtendimentosMarcado() {
        return listaTodosAtendimentosMarcadoUseCase.execute();
    }

    public List<AtendimentoMarcadoDTO> listaAtendimentoUsuario(UUID userId) {
        return listaAtendimentoMarcadoUserIdUseCase.execute(userId);
    }

    public void adicionaAtendimentoMarcado(AtendimentoMarcadoRequestDTO atendimentoMarcado) {
        adicionarAtendimentoMarcadoUseCase.execute(atendimentoMarcado);
    }

    public void atualizaStatusAtendimentoMarcado(UUID partnerId, Long atendimentoMarcadoId, AtendimentoStatusDTO request) {
        atualizaStatusDoAtendimentoMarcadoUseCase.execute(partnerId, atendimentoMarcadoId, request.status());
    }
}