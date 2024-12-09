package br.com.start.meupet.agendamento.usecase.AtendimentoMarcado;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoStatusDTO;
import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.exceptions.ForbiddenActionException;
import br.com.start.meupet.common.exceptions.SchedulingConflictException;
import br.com.start.meupet.common.exceptions.StatusInvalidException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AtualizaStatusDoAtendimentoMarcadoUseCase {

    private final AtendimentoMarcadoRepository atendimentoMarcadoRepository;
    private final UserRepository userRepository;

    public AtualizaStatusDoAtendimentoMarcadoUseCase(AtendimentoMarcadoRepository atendimentoMarcadoRepository, UserRepository userRepository) {
        this.atendimentoMarcadoRepository = atendimentoMarcadoRepository;
        this.userRepository = userRepository;
    }

    public void execute(UUID partnerId, Long atendimentoMarcadoId, String status) {
        AtendimentoStatus novoStatus;
        try {
            novoStatus = AtendimentoStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusInvalidException("Status inválido. Use PENDENTE, CANCELADO, CONFIRMADO ou CONCLUIDO");
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

        User user = atendimentoMarcado.getUser();
        if(status.equals("CONCLUIDO") && atendimentoMarcado.getStatus().equals(AtendimentoStatus.CONFIRMADO)) {
            user.setMoedaCapiba(user.getMoedaCapiba() + 10);
        }

        // Atualiza o status do atendimento
        atendimentoMarcado.setStatus(novoStatus);
        userRepository.save(user);
        atendimentoMarcadoRepository.save(atendimentoMarcado);
    }
}