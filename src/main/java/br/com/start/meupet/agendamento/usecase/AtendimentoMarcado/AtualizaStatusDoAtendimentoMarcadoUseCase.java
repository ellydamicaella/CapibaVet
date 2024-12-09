package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoStatusDTO;
import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.exceptions.ForbiddenActionException;
import br.com.start.meupet.common.exceptions.StatusInvalidException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AtualizaStatusDoAtendimentoMarcadoUseCase {

    private final AtendimentoMarcadoRepository atendimentoMarcadoRepository;

    public AtualizaStatusDoAtendimentoMarcadoUseCase(AtendimentoMarcadoRepository atendimentoMarcadoRepository) {
        this.atendimentoMarcadoRepository = atendimentoMarcadoRepository;
    }

    public void execute(UUID partnerId, Long atendimentoMarcadoId, AtendimentoStatusDTO request) {
        AtendimentoStatus novoStatus;
        try {
            novoStatus = AtendimentoStatus.valueOf(request.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusInvalidException("Status inválido. Use PENDENTE, CANCELADO ou CONFIRMADO.");
        }

        // Verifica se o atendimento existe
        AtendimentoMarcado atendimentoMarcado = atendimentoMarcadoRepository.findById(atendimentoMarcadoId)
                .orElse(null);

        if (atendimentoMarcado == null) {
            throw new EntityNotFoundException("Atendimento não encontrado");
        }

        // Verifica se o parceiro é o dono do atendimento
        if (!atendimentoMarcado.getPartner().getId().equals(partnerId)) {
            throw new ForbiddenActionException("Você não tem permissão para alterar este atendimento.");
        }

        // Atualiza o status do atendimento
        atendimentoMarcado.setStatus(novoStatus);
        atendimentoMarcadoRepository.save(atendimentoMarcado);
    }
}