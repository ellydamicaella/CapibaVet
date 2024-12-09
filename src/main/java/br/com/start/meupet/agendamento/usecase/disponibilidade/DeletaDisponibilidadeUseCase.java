package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.agendamento.repository.DisponibilidadeRepository;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeletaDisponibilidadeUseCase {

    private final PartnerRepository partnerRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;

    public DeletaDisponibilidadeUseCase(PartnerRepository partnerRepository, DisponibilidadeRepository disponibilidadeRepository) {
        this.partnerRepository = partnerRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    public void execute(UUID partnerId, Long disponibilidadeId) {
        Optional<Partner> partnerOpt = partnerRepository.findById(partnerId);
        if (partnerOpt.isEmpty()) {
            throw new EntityNotFoundException("Parceiro nao encontrado");
        }
        Partner partner = partnerOpt.get();
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(disponibilidadeId);
        disponibilidadeRepository.delete(disponibilidade);
    }
}