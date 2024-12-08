package br.com.start.meupet.agendamento.facade;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.usecase.AtendimentoMarcado.ListaAtendimentoMarcadoUserIdUseCase;
import br.com.start.meupet.agendamento.usecase.AtendimentoMarcado.ListaTodosAtendimentosMarcadoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AtendimentoMarcadoFacade {
@Autowired
    ListaTodosAtendimentosMarcadoUseCase listaTodosAtendimentosMarcadoUseCase;
 @Autowired
    ListaAtendimentoMarcadoUserIdUseCase listaAtendimentoMarcadoUserIdUseCase;
    public List<AtendimentoMarcadoDTO> ListaTodosAtendimentosMarcado() {
       return listaTodosAtendimentosMarcadoUseCase.execute();
    }


    public List<AtendimentoMarcadoDTO> ListaAtendimentoUsuario(UUID userId) {
        return listaAtendimentoMarcadoUserIdUseCase.execute(userId);
    }
}
