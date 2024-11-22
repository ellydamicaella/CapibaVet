package br.com.start.meupet.common.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.interfaces.Authenticable;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ResetPasswordUseCase {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordUseCase(JwtUtils jwtUtils, UserRepository userRepository, PartnerRepository partnerRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(String token, String newPassword) {
        Claims parsedToken = jwtUtils.getParsedToken(token);
        String email = parsedToken.getSubject();
        Optional<Authenticable> authenticable = findUserByEmail(new Email(email));

        log.info("email: {}", email);

        if(authenticable.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        log.info("password: {}", newPassword);
        authenticable.get().setPassword(passwordEncoder.encode(newPassword));

        log.info("password encrypted: {}", authenticable.get().getPassword());

        if(authenticable.get() instanceof User user) {

            User newUser = userRepository.save(user);
           log.info(newUser.toString());
        } else if (authenticable.get() instanceof Partner partner) {
            partnerRepository.save(partner);
        }
    }

    public Optional<Authenticable> findUserByEmail(Email email) {
        Optional<Authenticable> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isEmpty()) {
            Optional<Authenticable> partner = Optional.ofNullable(partnerRepository.findByEmail(email));
            return partner;
        }
        return user;
    }

}
