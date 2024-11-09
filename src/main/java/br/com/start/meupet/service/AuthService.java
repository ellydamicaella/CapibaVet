package br.com.start.meupet.service;

import br.com.start.meupet.dto.AccessDTO;
import br.com.start.meupet.dto.AuthenticationDTO;
import br.com.start.meupet.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public AuthService(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public AccessDTO login(AuthenticationDTO authDTO) {

        try {
            // Cria mecanismo de credencial para o Spring
            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authDTO.email(),
                    authDTO.password());

            // Prepara mecanismo para autenticacao
            Authentication authentication = authenticationManager.authenticate(userAuth);

            // Busca usuario logado
            UserDetailsImpl userAuthenticate = (UserDetailsImpl) authentication.getPrincipal();

            String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);

            AccessDTO accessDTO = new AccessDTO(token);
            log.info("Usuario logado com sucesso token: {}", token);
            return accessDTO;
        } catch (BadCredentialsException e) {
            // TODO: handle exception
        }

        return new AccessDTO("Acesso negado");
    }
}
