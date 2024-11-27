package br.com.start.meupet.auth.usecase.authenticable;

import br.com.start.meupet.auth.interfaces.Authenticable;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindAuthenticableUseCase {

    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;

    public FindAuthenticableUseCase(PartnerRepository partnerRepository, UserRepository userRepository) {
        this.partnerRepository = partnerRepository;
        this.userRepository = userRepository;
    }

    public Optional<Authenticable> byEmail(Email email) {
        Optional<Authenticable> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isEmpty()) {
            Optional<Authenticable> partner = Optional.ofNullable(partnerRepository.findByEmail(email));
            return partner;
        }
        return user;
    }
}
