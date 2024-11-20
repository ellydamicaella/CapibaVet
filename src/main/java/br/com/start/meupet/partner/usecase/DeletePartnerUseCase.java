package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class DeletePartnerUseCase {

    private final PartnerRepository partnerRepository;

    public DeletePartnerUseCase(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Transactional
    public void execute(UUID id) {
        Partner partnerEntity = partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        partnerRepository.delete(partnerEntity);
        log.info("Parceiro deletado :{}", partnerEntity);
    }
}
