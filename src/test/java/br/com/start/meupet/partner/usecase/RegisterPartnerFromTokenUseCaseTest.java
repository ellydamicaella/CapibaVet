package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.exceptions.EntityConflictException;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterPartnerFromTokenUseCaseTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private ServiceUtils serviceUtils;

    @InjectMocks
    private RegisterPartnerFromTokenUseCase registerPartnerFromTokenUseCase;

    @Test
    @DisplayName("Deve registrar o parceiro com sucesso")
    void registerUserFromTokenUseCase1() {
        Claims claims = Mockito.mock(Claims.class);
        when(claims.getSubject()).thenReturn("partner@gmail.com");
        when(claims.get("name", String.class)).thenReturn("Partner Example");
        when(claims.get("phoneNumber", String.class)).thenReturn("(81) 99999-9999");
        when(claims.get("password", String.class)).thenReturn("password123");
        when(claims.get("document", String.class)).thenReturn("11111111111111");
        when(claims.get("documentType", String.class)).thenReturn("CNPJ");

        Partner partner = new Partner();
        partner.setId(UUID.randomUUID());
        partner.setName("Partner Example");
        partner.setEmail(new Email("partner@gmail.com"));

        when(partnerRepository.save(any(Partner.class))).thenReturn(partner);

        Partner result = registerPartnerFromTokenUseCase.execute(claims);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Partner Example", result.getName());
        Assertions.assertEquals("partner@gmail.com", result.getEmail().toString());
        verify(partnerRepository, times(1)).save(any(Partner.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar registrar um parceiro que já existe")
    void shouldThrowExceptionIfUserAlreadyExists() {
        Claims claims = Mockito.mock(Claims.class);
        when(claims.getSubject()).thenReturn("partner@gmail.com");
        when(claims.get("name", String.class)).thenReturn("Existing Partner");
        when(claims.get("phoneNumber", String.class)).thenReturn("(81) 99999-9999");
        when(claims.get("password", String.class)).thenReturn("password123");
        when(claims.get("document", String.class)).thenReturn("11111111111111");
        when(claims.get("documentType", String.class)).thenReturn("CNPJ");

        doThrow(new EntityConflictException("Partner already exists"))
                .when(serviceUtils).isPartnerAlreadyExists(any(Partner.class));

        ProblemDetailsException exception = Assertions.assertThrows(
                ProblemDetailsException.class,
                () -> registerPartnerFromTokenUseCase.execute(claims)
        );

        Assertions.assertInstanceOf(String.class, exception.getMessage(), "A mensagem da exceção deve ser do tipo String");

//        when(serviceUtils.isUserAlreadyExists(any(User.class)));

//        verify(serviceUtils, times(1)).isUserAlreadyExists(any(User.class));
        verify(partnerRepository, never()).save(any(Partner.class));
    }
}
