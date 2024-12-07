package br.com.start.meupet.auth.usecase;

import br.com.start.meupet.auth.dto.AccessDTO;
import br.com.start.meupet.auth.dto.AuthenticationDTO;
import br.com.start.meupet.auth.service.AuthenticableDetailsImpl;
import br.com.start.meupet.auth.usecase.auth.LoginUseCase;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginUseCase loginUseCase;


    @Test
    void shouldReturnAccessDTOWhenCredentialsAreValid() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String token = "mockedToken";

        AuthenticationDTO authDTO = new AuthenticationDTO(email, password);
        UsernamePasswordAuthenticationToken userAuth =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = mock(Authentication.class);

        AuthenticableDetailsImpl userDetails =
                (AuthenticableDetailsImpl)
                authentication.getPrincipal();


        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(userAuth)).thenReturn(authentication);
        when(jwtUtils.generateTokenFromAuthenticableDetailsImpl(userDetails)).thenReturn(token);

        // Act
        AccessDTO result = loginUseCase.execute(authDTO);

        // Assert
        assertNotNull(result);
        assertEquals(token, result.token());
        verify(authenticationManager).authenticate(userAuth);
        verify(jwtUtils).generateTokenFromAuthenticableDetailsImpl(userDetails);
    }


}
