package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.agendamento.dto.disponibilidade.PartnerDisponibilidadeDTO;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetPartnerAndAvailabilities {

    private final PartnerRepository partnerRepository;

    public GetPartnerAndAvailabilities(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public PartnerDisponibilidadeDTO execute(UUID partnerId) {
        Optional<Partner> partners = partnerRepository.findById(partnerId);
        if(partners.isEmpty()) {
            throw new ProblemDetailsException("Parceiro nao encontrado", "Parceiro nao existe", HttpStatus.NOT_FOUND);
        }
        return new PartnerDisponibilidadeDTO(partners.get());
    }
}
