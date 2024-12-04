package br.com.start.meupet.auth.usecase.authenticable;

import br.com.start.meupet.auth.dto.AuthenticableDTO;
import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.dto.PartnerDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.dto.UserDTO;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ChangeAuthenticablePasswordUseCase {

    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangeAuthenticablePasswordUseCase(UserRepository userRepository, PartnerRepository partnerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(AuthenticableDTO response, UUID id, String newPassword) {
        switch (response) {
            case null -> throw new EntityNotFoundException("User not Found"); // Caso o usuário não seja encontrado
            case PartnerDTO partnerDTO -> {
                Partner partner = new Partner();
                partner.setId(id);
                partner.setPassword(passwordEncoder.encode(newPassword));
                partnerRepository.save(partner);
                log.info("Senha do parceiro alterada com sucesso!");
                return "Senha do parceiro alterada com sucesso!";
            }
            case UserDTO userDTO -> {
                User user = new User();
                user.setId(id);
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setName(userDTO.getName());
                user.setSocialName(userDTO.getSocialName());
                user.setDateOfBirth(BirthDayUtils.convertToDate(userDTO.getDateOfBirth()));
                user.setEmail(new Email(userDTO.getEmail()));
                user.setPhoneNumber(new PhoneNumber(userDTO.getPhoneNumber()));
                user.setPersonalRegistration(new PersonalRegistration(userDTO.getDocument(), DocumentType.valueOf(userDTO.getDocumentType())));
                user.setAnimals(userDTO.getAnimals());
                user.setAtendimentoMarcados(userDTO.getAtendimentos());

                userRepository.save(user);
                log.info("Senha do usuario alterada com sucesso!");
                return "Senha do usuario alterada com sucesso!";
            }
            default -> {
                return "default";
            }
        }
    }

}
