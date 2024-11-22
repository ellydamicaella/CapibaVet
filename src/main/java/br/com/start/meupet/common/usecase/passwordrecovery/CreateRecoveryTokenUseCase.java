package br.com.start.meupet.common.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.interfaces.Authenticable;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.common.service.EmailService;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CreateRecoveryTokenUseCase {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;
    private final PartnerRepository partnerRepository;

    public CreateRecoveryTokenUseCase(UserRepository userRepository, EmailService emailService, JwtUtils jwtUtils, PartnerRepository partnerRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.jwtUtils = jwtUtils;
        this.partnerRepository = partnerRepository;
    }

    @Transactional
    public void execute(String email) {
        //pega usuario
        Optional<Authenticable> authenticable = findUserByEmail(new Email(email));
        if (authenticable.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        //gera o token
        String token = jwtUtils.generateTokenForPasswordRecovery(authenticable.get().getEmail());
        // cria o token e sobe para o banco de dados
        // envia o email para o usuario
        sendVerificationEmail(authenticable.get(), token);
    }

    public Optional<Authenticable> findUserByEmail(Email email) {
        Optional<Authenticable> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isEmpty()) {
            Optional<Authenticable> partner = Optional.ofNullable(partnerRepository.findByEmail(email));
            return partner;
        }
        return user;
    }


    private void sendVerificationEmail(Authenticable authenticable, String token) {
        emailService.sendEmailPasswordRecoveryTemplate(
                authenticable.getEmail().toString(),
                "Recuperação de senha",
                authenticable.getName(),
                token
        );
    }

}
