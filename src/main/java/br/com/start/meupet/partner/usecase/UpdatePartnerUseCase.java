package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.partner.dto.PartnerRequestDTO;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.partner.mapper.PartnerMapper;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class UpdatePartnerUseCase {

    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceUtils serviceUtils;

    public UpdatePartnerUseCase(PartnerRepository partnerRepository, PasswordEncoder passwordEncoder, ServiceUtils serviceUtils) {
        this.partnerRepository = partnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.serviceUtils = serviceUtils;
    }

    @Transactional
    public PartnerResponseDTO execute(UUID id, PartnerRequestDTO newPartner) {
        Partner partnerEntity = partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Partner not found"));

        validatePartner(partnerEntity);

        Partner updatedPartner = PartnerMapper.partnerBeforeToNewPartner(partnerEntity, PartnerMapper.partnerRequestToPartner(newPartner));
        encodePassword(updatedPartner, updatedPartner.getPassword());
        partnerRepository.save(updatedPartner);

        log.info("Parceiro atualizado :{}", updatedPartner);
        return PartnerMapper.partnerToResponseDTO(updatedPartner);
    }

    private void validatePartner(Partner partnerEntity) {
        serviceUtils.isPartnerAlreadyExists(partnerEntity);
    }

    private void encodePassword(Partner partnerEntity, String rawPassword) {
        partnerEntity.setPassword(passwordEncoder.encode(rawPassword));
    }
}
