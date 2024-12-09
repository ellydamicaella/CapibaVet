package br.com.start.meupet.agendamento.usecase.servico;

import br.com.start.meupet.agendamento.dto.servico.PartnerServicoDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListAllServicesAndPartnersUseCase {

    private final PartnerRepository partnerRepository;

    public ListAllServicesAndPartnersUseCase(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<PartnerServicoDTO> execute() {
        List<Partner> partners = partnerRepository.findAll();
        List<PartnerServicoDTO> partnersDTO = new ArrayList<>();
        partners.forEach(partner -> {
            partnersDTO.add(new PartnerServicoDTO(partner));
        });
        return partnersDTO;
    }
}
