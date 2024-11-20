package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.partner.mapper.PartnerMapper;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindPartnerByIdUseCase {

    private final PartnerRepository partnerRepository;

    public FindPartnerByIdUseCase(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public PartnerResponseDTO execute(UUID id) {
        Partner partnerEntity = partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        return PartnerMapper.partnerToResponseDTO(partnerEntity);
    }
}
