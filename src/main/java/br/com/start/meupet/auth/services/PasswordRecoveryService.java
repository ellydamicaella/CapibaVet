package br.com.start.meupet.auth.services;

import br.com.start.meupet.auth.usecase.passwordrecovery.CreateRecoveryTokenUseCase;
import br.com.start.meupet.auth.usecase.passwordrecovery.ResetPasswordUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class PasswordRecoveryService {

    private final CreateRecoveryTokenUseCase createRecoveryTokenUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    public PasswordRecoveryService(CreateRecoveryTokenUseCase createRecoveryTokenUseCase, ResetPasswordUseCase resetPasswordUseCase) {
        this.createRecoveryTokenUseCase = createRecoveryTokenUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    public void generateRecoveryToken(String email) {
        createRecoveryTokenUseCase.execute(email);
    }

    public void resetPassword(String token, String newPassword) {
        resetPasswordUseCase.execute(token, newPassword);
    }

    public String loadPage(String token) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/changePassword.html");
            String template = new String(classPathResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{token}}", token);
            log.info("gerando o template changePassword");
            return template;
        } catch (IOException e) {
            log.error("Não foi possivel gerar o template changePassword");
            return null;
        }
    }
}
