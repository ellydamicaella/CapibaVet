package br.com.start.meupet.auth.service;

import br.com.start.meupet.auth.dto.AuthenticableDTO;
import br.com.start.meupet.auth.interfaces.Authenticable;
import br.com.start.meupet.auth.interfaces.AuthenticableResponseDTO;
import br.com.start.meupet.auth.usecase.authenticable.FindAuthenticableUseCase;
import br.com.start.meupet.auth.usecase.authenticable.ProcessUserRegistrationUseCase;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.dto.PartnerDTO;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.dto.UserDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class AuthenticableService {

    private final ProcessUserRegistrationUseCase processUserRegistrationUseCase;
    private final FindAuthenticableUseCase findAuthenticableUseCase;

    // Cache para armazenar templates carregados
    private final Map<String, String> templateCache = new ConcurrentHashMap<>();

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final EmailService emailService;

    public AuthenticableService(ProcessUserRegistrationUseCase processUserRegistrationUseCase, FindAuthenticableUseCase findAuthenticableUseCase, EmailService emailService) {
        this.processUserRegistrationUseCase = processUserRegistrationUseCase;
        this.findAuthenticableUseCase = findAuthenticableUseCase;
        this.emailService = emailService;
    }

    @Transactional
    public void processUserRegistration(String token) { processUserRegistrationUseCase.execute(token); }

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

//    public String generateTemplateToConfirmAccount(String token) {
//        executor.submit(() -> {
//            // Verifica se o template já está no cache
//            String template = templateCache.computeIfAbsent("confirmacaoConta.html", emailService::loadTemplateFromFile);
//            log.info("Está no cache!");
//            return template.replace("{{token}}", token);
//        });
//        return null;
//    }

    public AuthenticableDTO findUserByEmail(String email) {
        Optional<Authenticable> authenticable = findAuthenticableUseCase.byEmail(new Email(email));
        return authenticable
                .map(auth -> {
                    if (auth instanceof Partner partner) {
                        return new PartnerDTO(partner);
                    } else if (auth instanceof User user) {
                        return new UserDTO(user);
                    } else {
                        return null; // Caso não seja Partner nem User, pode lançar uma exceção ou lidar de outra forma
                    }
                })
                .orElse(null);
    }
}
