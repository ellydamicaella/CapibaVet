package br.com.start.meupet.passwordRecovery.service;

import br.com.start.meupet.passwordRecovery.dto.PasswordRecoveryRequestDTO;
import br.com.start.meupet.passwordRecovery.usecase.CreateRecoveryTokenUseCase;
import br.com.start.meupet.passwordRecovery.usecase.ResetPasswordUseCase;
import org.springframework.stereotype.Service;

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
