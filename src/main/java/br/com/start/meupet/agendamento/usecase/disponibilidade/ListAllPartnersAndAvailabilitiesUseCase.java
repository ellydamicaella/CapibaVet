package br.com.start.meupet.agendamento.usecase.disponibilidade;

import br.com.start.meupet.agendamento.dto.disponibilidade.PartnerDisponibilidadeDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListAllPartnersAndAvailabilitiesUseCase {

    private final PartnerRepository partnerRepository;

    public ListAllPartnersAndAvailabilitiesUseCase(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<PartnerDisponibilidadeDTO> execute() {
        List<Partner> partners = partnerRepository.findAll();
        List<PartnerDisponibilidadeDTO> partnersDTO = new ArrayList<>();
        partners.forEach(partner -> {
            partnersDTO.add(new PartnerDisponibilidadeDTO(partner));
        });
        return partnersDTO;
    }

}
