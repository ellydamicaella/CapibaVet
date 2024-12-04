package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.exceptions.EntityConflictException;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
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
public class RegisterUserFromTokenUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceUtils serviceUtils;

    @InjectMocks
    private RegisterUserFromTokenUseCase registerUserFromTokenUseCase;

    @Test
    @DisplayName("Deve registrar o usuário com sucesso")
    void registerUserFromTokenUseCase1() {
        Claims claims = Mockito.mock(Claims.class);
        when(claims.getSubject()).thenReturn("user@gmail.com");
        when(claims.get("name", String.class)).thenReturn("User Example");
        when(claims.get("socialName", String.class)).thenReturn("User");
        when(claims.get("phoneNumber", String.class)).thenReturn("(81) 99999-9999");
        when(claims.get("password", String.class)).thenReturn("password123");
        when(claims.get("document", String.class)).thenReturn("11111111111");
        when(claims.get("documentType", String.class)).thenReturn("CPF");
        when(claims.get("birthDate", String.class)).thenReturn("2000-01-01");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("User Example");
        user.setSocialName("User");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = registerUserFromTokenUseCase.execute(claims);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("User Example", result.getName());
        Assertions.assertEquals("User", result.getSocialName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar registrar um usuário que já existe")
    void shouldThrowExceptionIfUserAlreadyExists() {
        Claims claims = Mockito.mock(Claims.class);
        when(claims.getSubject()).thenReturn("user@gmail.com");
        when(claims.get("name", String.class)).thenReturn("Existing User");
        when(claims.get("socialName", String.class)).thenReturn("User");
        when(claims.get("phoneNumber", String.class)).thenReturn("(81) 99999-9999");
        when(claims.get("password", String.class)).thenReturn("password123");
        when(claims.get("document", String.class)).thenReturn("11111111111");
        when(claims.get("documentType", String.class)).thenReturn("CPF");
        when(claims.get("birthDate", String.class)).thenReturn("2000-01-01");

        doThrow(new EntityConflictException("User already exists"))
                .when(serviceUtils).isUserAlreadyExists(any(User.class));

        ProblemDetailsException exception = Assertions.assertThrows(
                ProblemDetailsException.class,
                () -> registerUserFromTokenUseCase.execute(claims)
        );

        Assertions.assertInstanceOf(String.class, exception.getMessage(), "A mensagem da exceção deve ser do tipo String");

//        when(serviceUtils.isUserAlreadyExists(any(User.class)));

//        verify(serviceUtils, times(1)).isUserAlreadyExists(any(User.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve preencher nome social automaticamente se não fornecido")
    void shouldAutomaticallyFillSocialNameIfNotProvided() {
        Claims claims = Mockito.mock(Claims.class);
        when(claims.getSubject()).thenReturn("user@example.com");
        when(claims.get("name", String.class)).thenReturn("User Example");
        when(claims.get("socialName", String.class)).thenReturn(null);
        when(claims.get("phoneNumber", String.class)).thenReturn("(81) 99999-9999");
        when(claims.get("password", String.class)).thenReturn("password123");
        when(claims.get("document", String.class)).thenReturn("11111111111");
        when(claims.get("documentType", String.class)).thenReturn("CPF");
        when(claims.get("birthDate", String.class)).thenReturn("2000-01-01");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("User Example");
        user.setSocialName("User");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = registerUserFromTokenUseCase.execute(claims);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("User", result.getSocialName());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
