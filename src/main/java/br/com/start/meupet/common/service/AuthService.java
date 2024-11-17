package br.com.start.meupet.common.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import br.com.start.meupet.user.service.AuthenticableDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.common.dto.AccessDTO;
import br.com.start.meupet.common.dto.AuthenticationDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.service.mappers.UserMapper;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Autowired
    ServiceUtils serviceUtils;

    public AuthService(JwtUtils jwtUtils,
            AuthenticationManager authenticationManager,
            UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
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

    @Transactional
    public UserResponseDTO verifyNewUser(String token) {
        Claims parsedToken = jwtUtils.getParsedToken(token);

        String email = parsedToken.getSubject();
        String name = parsedToken.get("name", String.class);
        String phoneNumber = parsedToken.get("phone_number", String.class);
        String password = parsedToken.get("password", String.class);

        User userEntity = new User();
        userEntity.setEmail(new Email(email));
        userEntity.setName(name);
        userEntity.setPhoneNumber(new PhoneNumber(phoneNumber));
        userEntity.setPassword(password);

        serviceUtils.isUserAlreadyExists(userEntity);

        userRepository.save(userEntity);

        return UserMapper.userToResponseDTO(userEntity);
    }

    public String confirmAccount(String token) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/confirmacaoConta.html");
            String template = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{token}}", token);
            log.info("gerando o template confirmacaoConta");
            return template;
        } catch (IOException e) {
            log.error("Não foi possivel gerar o template confirmacaoConta");
            return null;
        }
    }
}
