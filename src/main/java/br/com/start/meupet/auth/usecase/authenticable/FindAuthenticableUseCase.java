package br.com.start.meupet.auth.usecase.authenticable;

import br.com.start.meupet.auth.interfaces.Authenticable;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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

    public Optional<Authenticable> byId(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.map(u -> u);  // Garantir que o User é tratado como Authenticable
        }

        Optional<Partner> partner = partnerRepository.findById(id);
        return partner.map(p -> p);  // Garantir que o Partner é tratado como Authenticable
    }
}
