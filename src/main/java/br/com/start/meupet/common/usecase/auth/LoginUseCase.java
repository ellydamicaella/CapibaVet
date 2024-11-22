package br.com.start.meupet.common.auth.usecase;

import br.com.start.meupet.common.dto.AccessDTO;
import br.com.start.meupet.common.dto.AuthenticationDTO;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.common.service.AuthenticableDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginUseCase {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public LoginUseCase(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public AccessDTO execute(AuthenticationDTO authDTO) {

        try {
            // Cria mecanismo de credencial para o Spring
            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authDTO.email(),
                    authDTO.password());
            // Prepara mecanismo para autenticacao
            Authentication authentication = authenticationManager.authenticate(userAuth);
            // Busca usuario logado
            AuthenticableDetailsImpl userAuthenticate = (AuthenticableDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateTokenFromAuthenticableDetailsImpl(userAuthenticate);
            AccessDTO accessDTO = new AccessDTO(token);
            log.info("Usuario logado com sucesso token: {}", token);
            return accessDTO;
        } catch (BadCredentialsException e) {
            // TODO: handle exception
        }
        return new AccessDTO("Acesso negado");
    }
}
