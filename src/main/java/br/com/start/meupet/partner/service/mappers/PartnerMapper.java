package br.com.start.meupet.mappers;

import br.com.start.meupet.domain.entities.Partner;
import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.interfaces.PersonalRegistration;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import br.com.start.meupet.dto.PartnerRequestDTO;
import br.com.start.meupet.dto.PartnerResponseDTO;

import java.time.LocalDateTime;

public final class PartnerMapper {
    public static PartnerResponseDTO partnerToResponseDTO(Partner partner) {
        return new PartnerResponseDTO(partner);
    }

    public static Partner partnerRequestToPartner(PartnerRequestDTO partner) {

        return new Partner(partner.getName(), new Email(partner.getEmail()), new PersonalRegistration(), partner.getPassword(), new PhoneNumber(partner.getPhoneNumber()));
    }

    public static Partner partnerBeforeToNewPartner(User oldUser, User newUser) {
        Partner user = new Partner(
                oldUser.getName(),
                newUser.getEmail(),
                newUser.getPassword(),
                newUser.getPhoneNumber()
        );
        user.setId(oldUser.getId());
        user.setCreatedAt(oldUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

}
