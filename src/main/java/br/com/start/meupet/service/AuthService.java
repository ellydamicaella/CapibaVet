package br.com.start.meupet.service;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.UserRepository;
import br.com.start.meupet.domain.repository.VerifyAuthenticableEntityRepository;
import br.com.start.meupet.dto.AccessDTO;
import br.com.start.meupet.dto.AuthenticationDTO;
import br.com.start.meupet.enums.SituationType;
import br.com.start.meupet.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final VerifyAuthenticableEntityRepository verifyAuthenticableEntityRepository;
    private final UserRepository userRepository;

    public AuthService(JwtUtils jwtUtils, AuthenticationManager authenticationManager, VerifyAuthenticableEntityRepository verifyAuthenticableEntityRepository, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.verifyAuthenticableEntityRepository = verifyAuthenticableEntityRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public String verifyNewUser(String uuid) {
        Optional<VerifyAuthenticableEntity> userVerified = Optional.ofNullable(verifyAuthenticableEntityRepository.findByUuid(UUID.fromString(uuid)));

        if (userVerified.isPresent()) {
            User userEntity = userVerified.get().getUser();
            if (userVerified.get().getExpirationDate().isAfter(LocalDateTime.now())) {
                userEntity.setSituationType(SituationType.ATIVO);
                userRepository.save(userEntity);
                verifyAuthenticableEntityRepository.delete(userVerified.get());
                log.info("Usuario verificado :{}", userEntity);
                return "Usuário Verificado";
            } else {
                verifyAuthenticableEntityRepository.delete(userVerified.get());
                userRepository.delete(userEntity);
                log.error("Tempo de verificação expirado : {}", userVerified.get());
                return "Tempo de verificação expirado";
            }

        } else {
            log.error("Usuario nao verificado :{}", uuid);
            return "Usuario nao verificado";
        }
    }

    public String confirmAccount(String uuid) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/confirmacaoConta.html");
            String template = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            template = template.replace("{{uuid}}", uuid);
            log.info("gerando o template confirmacaoConta");
            return template;
        } catch (IOException e) {
            log.error("Não foi possivel gerar o template confirmacaoConta");
            return null;
        }
    }
}
