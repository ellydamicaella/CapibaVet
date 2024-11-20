package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.partner.mapper.PartnerMapper;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ListPartnersUseCase {

    private final PartnerRepository partnerRepository;

    public ListPartnersUseCase(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<PartnerResponseDTO> execute(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Partner> partners = partnerRepository.findAll(pageable).getContent();
        log.info("Parceiros listados :{}", partners.stream().map(Partner::getId).collect(Collectors.toList()));
        return partners.stream().map(PartnerMapper::partnerToResponseDTO).toList();
    }
}
