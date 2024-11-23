package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.common.service.EmailService;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.utils.VerifyAuthenticable;
import br.com.start.meupet.partner.dto.PartnerRequestDTO;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.partner.mapper.PartnerMapper;
import br.com.start.meupet.partner.model.Partner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreatePartnerUseCase {

    private final ServiceUtils serviceUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    public CreatePartnerUseCase(ServiceUtils serviceUtils, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, EmailService emailService) {
        this.serviceUtils = serviceUtils;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    public PartnerResponseDTO execute(PartnerRequestDTO partnerRequest) {
        Partner partnerEntity = PartnerMapper.partnerRequestToPartner(partnerRequest);
        //valida parceiro
        validatePartner(partnerEntity);
        //criptografa senha
        encodePassword(partnerEntity, partnerRequest.getPassword());
        //gera Token com os dados do parceiro
        String token = generateVerificationToken(partnerEntity);
        // envia o email
        sendVerificationEmail(partnerEntity, token);
        log.info("Parceiro criado, aguardando a confirmação da conta :{}", partnerEntity);
        return PartnerMapper.partnerToResponseDTO(partnerEntity);
    }


    private void validatePartner(Partner partnerEntity) {
        serviceUtils.isPartnerAlreadyExists(partnerEntity);
    }

    private void encodePassword(Partner partnerEntity, String rawPassword) {
        partnerEntity.setPassword(passwordEncoder.encode(rawPassword));
    }

    private String generateVerificationToken(Partner partnerEntity) {
        return jwtUtils.generateTokenForPartnerVerifyAccount(partnerEntity);
    }

    private void sendVerificationEmail(Partner partner, String token) {
        VerifyAuthenticable verifyEntity = new VerifyAuthenticable(token);
        emailService.sendEmailConfirmAccountTemplate(
                partner.getEmail().toString(),
                "Novo parceiro cadastrado",
                partner.getName(),
                verifyEntity.getToken()
        );
    }

}
