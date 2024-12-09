package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.auth.service.EmailService;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.templates.TemplateNameEnum;
import br.com.start.meupet.partner.dto.PartnerRequestDTO;
import br.com.start.meupet.partner.model.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class InitPartnerRegistrationUseCaseTest {

    @Mock
    private ServiceUtils serviceUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private InitPartnerRegistrationUseCase initPartnerRegistrationUseCase;

    @Test
    void shouldExecuteSuccessfully() {
        PartnerRequestDTO partnerRequest = getPartnerRequestDTOAllIsOk();

        String encodedPassword = "encodedPassword123";
        String token = "testVerificationToken";

        Mockito.doNothing().when(serviceUtils).isPartnerAlreadyExists(Mockito.any(Partner.class));
        Mockito.when(passwordEncoder.encode(partnerRequest.getPassword())).thenReturn(encodedPassword);
        Mockito.when(jwtUtils.generateTokenForPartnerVerifyAccount(Mockito.any(Partner.class))).thenReturn(token);
        Mockito.doNothing().when(emailService).sendEmailTemplate(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(TemplateNameEnum.class),
                Mockito.anyString(),
                Mockito.anyString()
        );

        initPartnerRegistrationUseCase.execute(partnerRequest);

        Mockito.verify(serviceUtils).isPartnerAlreadyExists(Mockito.any(Partner.class));
        Mockito.verify(passwordEncoder).encode(partnerRequest.getPassword());
        Mockito.verify(jwtUtils).generateTokenForPartnerVerifyAccount(Mockito.any(Partner.class));
        Mockito.verify(emailService).sendEmailTemplate(
                partnerRequest.getEmail(),
                "Novo parceiro cadastrado",
                TemplateNameEnum.EMAIL_CONFIRM_ACCOUNT,
                partnerRequest.getName(),
                token
        );
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        PartnerRequestDTO partnerRequestDTO = getPartnerRequestDTOAllIsOk();

        Mockito.doThrow(new EntityNotFoundException("Partner already exists"))
                .when(serviceUtils).isPartnerAlreadyExists(Mockito.any(Partner.class));

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> initPartnerRegistrationUseCase.execute(partnerRequestDTO));

        Assertions.assertEquals("Not found", exception.getMessage());
        Mockito.verify(serviceUtils).isPartnerAlreadyExists(Mockito.any(Partner.class));
        Mockito.verifyNoInteractions(passwordEncoder, jwtUtils, emailService);
    }

    PartnerRequestDTO getPartnerRequestDTOAllIsOk() {
        PartnerRequestDTO newPartner = new PartnerRequestDTO();
        newPartner.setName("New Name");
        newPartner.setPassword("newPassword");
        newPartner.setEmail("aaaaa@gmail.com");
        newPartner.setPhoneNumber("(21) 22222-2222");
        newPartner.setDocument("11.111.111/1111-11");
        newPartner.setDocumentType("CNPJ");
        return newPartner;
    }
}
