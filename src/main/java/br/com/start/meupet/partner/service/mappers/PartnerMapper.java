package br.com.start.meupet.partner.service.mappers;

import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.dto.PartnerRequestDTO;
import br.com.start.meupet.partner.dto.PartnerResponseDTO;

import java.time.LocalDateTime;

public final class PartnerMapper {
    public static PartnerResponseDTO partnerToResponseDTO(Partner partner) {
        return new PartnerResponseDTO(partner);
    }

    public static Partner partnerRequestToPartner(PartnerRequestDTO partner) {
        PersonalRegistration registration = new PersonalRegistration(partner.getDocument(), partner.toDocumentType(partner.getDocumentType()));
        return new Partner(partner.getName(), new Email(partner.getEmail()), registration, partner.getPassword(), new PhoneNumber(partner.getPhoneNumber()));
    }

    public static Partner partnerBeforeToNewPartner(Partner oldPartner, Partner newPartner) {
        Partner partner = new Partner(
                oldPartner.getName(),
                newPartner.getEmail(),
                new PersonalRegistration(newPartner.getPersonalRegistration().getDocument(), newPartner.getPersonalRegistration().getType()),
                newPartner.getPassword(),
                newPartner.getPhoneNumber()
        );
        partner.setId(oldPartner.getId());
        partner.setCreatedAt(oldPartner.getCreatedAt());
        partner.setUpdatedAt(LocalDateTime.now());
        return partner;
    }

}
