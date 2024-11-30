package br.com.start.meupet.auth.service;

import br.com.start.meupet.auth.usecase.passwordrecovery.CreateRecoveryTokenUseCase;
import br.com.start.meupet.auth.usecase.passwordrecovery.ResetPasswordUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
