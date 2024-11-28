package br.com.start.meupet.auth.services;

import br.com.start.meupet.auth.interfaces.Authenticable;
import br.com.start.meupet.auth.interfaces.AuthenticableResponseDTO;
import br.com.start.meupet.auth.usecase.authenticable.FindAuthenticableUseCase;
import br.com.start.meupet.auth.usecase.authenticable.ProcessUserRegistrationUseCase;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
public class AuthenticableService {

    private final ProcessUserRegistrationUseCase processUserRegistrationUseCase;
    private final FindAuthenticableUseCase findAuthenticableUseCase;

    public AuthenticableService(ProcessUserRegistrationUseCase processUserRegistrationUseCase, FindAuthenticableUseCase findAuthenticableUseCase) {
        this.processUserRegistrationUseCase = processUserRegistrationUseCase;
        this.findAuthenticableUseCase = findAuthenticableUseCase;
    }

    @Transactional
    public AuthenticableResponseDTO processUserRegistration(String token) {
       return processUserRegistrationUseCase.execute(token);
    }

    public String generateTemplateToConfirmAccount(String token) {
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

    public AuthenticableResponseDTO findUserByEmail(String email) {
        Optional<Authenticable> authenticable = findAuthenticableUseCase.byEmail(new Email(email));
        return authenticable
                .map(auth -> {
                    if (auth instanceof Partner partner) {
                        return new PartnerResponseDTO(partner);
                    } else if (auth instanceof User user) {
                        return new UserResponseDTO(user);
                    } else {
                        return null; // Caso não seja Partner nem User, pode lançar uma exceção ou lidar de outra forma
                    }
                })
                .orElse(null);
    }
}
