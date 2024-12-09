package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListaTodosAtendimentosMarcadoUseCase {
    @Autowired
    AtendimentoMarcadoRepository atendimentoMarcadoRepository;

    public List<AtendimentoMarcadoDTO> execute() {
        List<AtendimentoMarcado> atendimentoMarcados = atendimentoMarcadoRepository.findAll();
        List<AtendimentoMarcadoDTO> atendimentoMarcadoDTO = new ArrayList<>();
        atendimentoMarcados.forEach(atendimento -> {
            atendimentoMarcadoDTO.add(new AtendimentoMarcadoDTO(atendimento, atendimento.getPartner(), atendimento.getUser(), atendimento.getServicoPrestado(), atendimento.getAnimal()));
        });
        return atendimentoMarcadoDTO;
    }
}
