package br.com.start.meupet.auth.usecase.passwordrecovery;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.auth.interfaces.Authenticable;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.auth.service.EmailService;
import br.com.start.meupet.auth.usecase.authenticable.FindAuthenticableUseCase;
import br.com.start.meupet.common.templates.TemplateNameEnum;
import br.com.start.meupet.common.valueobjects.Email;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CreateRecoveryTokenUseCase {

    private final EmailService emailService;
    private final JwtUtils jwtUtils;
    private final FindAuthenticableUseCase findAuthenticableUseCase;

    public CreateRecoveryTokenUseCase(
                EmailService emailService,
                JwtUtils jwtUtils,
                FindAuthenticableUseCase findAuthenticableUseCase
    ) {
        this.emailService = emailService;
        this.jwtUtils = jwtUtils;
        this.findAuthenticableUseCase = findAuthenticableUseCase;
    }

    @Transactional
    public void execute(String email) {
        //pega usuario
        Optional<Authenticable> authenticable = findAuthenticableUseCase.byEmail(new Email(email));
        if (authenticable.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        //gera o token
        String token = jwtUtils.generateTokenForPasswordRecovery(authenticable.get().getEmail());
        // cria o token e sobe para o banco de dados
        // envia o email para o usuario
        sendVerificationEmail(authenticable.get(), token);
    }

    private void sendVerificationEmail(Authenticable authenticable, String token) {
        emailService.sendEmailTemplate(
                authenticable.getEmail().toString(),
                "Recuperação de senha",
                TemplateNameEnum.EMAIL_PASSWORD_RECOVERY,
                authenticable.getName(),
                token
        );
    }

}
