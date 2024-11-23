package br.com.start.meupet.common.service;

import br.com.start.meupet.common.interfaces.AuthenticableResponseDTO;
import br.com.start.meupet.common.usecase.authenticable.ProcessUserRegistrationUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class AuthenticableService {

    private final ProcessUserRegistrationUseCase processUserRegistrationUseCase;

    public AuthenticableService(ProcessUserRegistrationUseCase processUserRegistrationUseCase) {
        this.processUserRegistrationUseCase = processUserRegistrationUseCase;
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
}
