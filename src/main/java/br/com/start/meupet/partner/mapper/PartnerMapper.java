package br.com.start.meupet.partner.mapper;

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
        return new Partner(partner.getName(),
                new Email(partner.getEmail()),
                partner.getPassword(),
                new PhoneNumber(partner.getPhoneNumber()),
                new PersonalRegistration(partner.getDocument(), partner.toDocumentType(partner.getDocumentType()))
        );
    }

    public static Partner partnerBeforeToNewPartner(Partner oldPartner, Partner newPartner) {
        Partner partner = new Partner(
                newPartner.getName(),
                newPartner.getEmail(),
                newPartner.getPassword(),
                newPartner.getPhoneNumber(),
                new PersonalRegistration(newPartner.getPersonalRegistration().getDocument(), newPartner.getPersonalRegistration().getDocumentType())
        );
        partner.setId(oldPartner.getId());
        partner.setCreatedAt(oldPartner.getCreatedAt());
        partner.setUpdatedAt(LocalDateTime.now());
        return partner;
    }


}
