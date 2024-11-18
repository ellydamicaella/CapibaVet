package br.com.start.meupet.partner.service;

import br.com.start.meupet.common.service.utils.VerifyAuthenticable;
import br.com.start.meupet.partner.dto.PartnerRequestDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.common.service.EmailService;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.partner.service.mappers.PartnerMapper;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PartnerService {

    private static final Logger log = LoggerFactory.getLogger(PartnerService.class);
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ServiceUtils serviceUtils;

    public PartnerService(
            PartnerRepository partnerRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService, ServiceUtils serviceUtils) {
        this.partnerRepository = partnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.serviceUtils = serviceUtils;
    }

    public List<PartnerResponseDTO> listAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Partner> partners = partnerRepository.findAll(pageable).getContent();
        log.info("Parceiros listados :{}", partners.stream().map(Partner::getId).collect(Collectors.toList()));
        return partners.stream().map(PartnerResponseDTO::new).toList();
    }

    public PartnerResponseDTO getPartnerById(UUID id) {
        Partner partnerEntity = partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        return PartnerMapper.partnerToResponseDTO(partnerEntity);
    }

    @Transactional
    public PartnerResponseDTO insert(PartnerRequestDTO partnerRequest) {
        Partner partnerEntity = PartnerMapper.partnerRequestToPartner(partnerRequest);

        serviceUtils.isPartnerAlreadyExists(partnerEntity);

        partnerEntity.setPassword(passwordEncoder.encode(partnerRequest.getPassword()));

        // partnerRepository.save(partnerEntity);

        log.info("Parceiro criado, aguardando a confirmação da conta :{}", partnerEntity);

        log.info("getEmail :{}", partnerEntity.getEmail().toString());
        log.info("getName :{}", partnerEntity.getName());
        log.info("getPhoneNumber :{}", partnerEntity.getPhoneNumber().toString());
        log.info("getPassword :{}", partnerEntity.getPassword());
        log.info("getDocument :{}", partnerEntity.getPersonalRegistration().getDocument());
        log.info("getDocumentType :{}", partnerEntity.getPersonalRegistration().getType());

        String token = new JwtUtils().generateTokenForPartnerVerifyAccount(
                partnerEntity.getEmail().toString(),
                partnerEntity.getName(),
                partnerEntity.getPhoneNumber().toString(),
                partnerEntity.getPassword(),
                partnerEntity.getPersonalRegistration().getDocument(),
                partnerEntity.getPersonalRegistration().getType().toString().toUpperCase());

        log.info("token :{}", token);

        VerifyAuthenticable verifyEntity = new VerifyAuthenticable(token);

        log.info("verifyEntity :{}", verifyEntity);

        emailService.sendEmailTemplate(partnerEntity.getEmail().toString(), "Novo parceiro cadastrado",
                partnerEntity.getName(), verifyEntity.getToken());

        return PartnerMapper.partnerToResponseDTO(partnerEntity);
    }

    @Transactional
    public PartnerResponseDTO update(UUID id, PartnerRequestDTO newPartner) {
        Partner partnerEntity = partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Partner not found"));

        serviceUtils.isPartnerAlreadyExists(partnerEntity);

        Partner updatedPartner = PartnerMapper.partnerBeforeToNewPartner(partnerEntity, PartnerMapper.partnerRequestToPartner(newPartner));
        updatedPartner.setPassword(passwordEncoder.encode(newPartner.getPassword()));
        partnerRepository.save(updatedPartner);

        log.info("Parceiro atualizado :{}", updatedPartner);
        return PartnerMapper.partnerToResponseDTO(updatedPartner);
    }

    @Transactional
    public void delete(UUID id) {
        Partner partnerEntity = partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        partnerRepository.delete(partnerEntity);
        log.info("Parceiro deletado :{}", partnerEntity);
    }
}
