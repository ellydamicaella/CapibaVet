package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.dto.PartnerUpdateDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FinalizaUltimosDadosDoParceiro {

    private final PartnerRepository partnerRepository;

    public FinalizaUltimosDadosDoParceiro(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public void execute(UUID partnerId, PartnerUpdateDTO partnerRequest) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new EntityNotFoundException("Parceiro não encontrado com ID: " + partnerId));

        // Atualiza apenas os campos não nulos do DTO
        if (partnerRequest.getName() != null) {
            partner.setName(partnerRequest.getName());
        }
        if (partnerRequest.getPhoneNumber() != null) {
            partner.setPhoneNumber(new PhoneNumber(partnerRequest.getPhoneNumber()));
        }
        if (partnerRequest.getStreetAndNumber() != null) {
            partner.setStreetAndNumber(partnerRequest.getStreetAndNumber());
        }
        if (partnerRequest.getNeighborhood() != null) {
            partner.setNeighborhood(partnerRequest.getNeighborhood());
        }

        partnerRepository.save(partner); // Persistindo as alterações no banco
    }
}