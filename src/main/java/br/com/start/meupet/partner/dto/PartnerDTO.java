package br.com.start.meupet.partner.dto;

import br.com.start.meupet.auth.dto.AuthenticableDTO;
import br.com.start.meupet.partner.model.Partner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDTO extends AuthenticableDTO {

    private UUID id;

    private String name;

    private String email;

    private String document;

    private String documentType;

    private String phoneNumber;

    private String streetAndNumber;

    private String neighborhood;


    public PartnerDTO(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.email = partner.getEmail().toString();
        this.document = partner.getPersonalRegistration().getDocument();
        this.documentType = partner.getPersonalRegistration().getDocumentType().toString();
        this.phoneNumber = partner.getPhoneNumber().toString();
        this.streetAndNumber = partner.getStreetAndNumber();
        this.neighborhood = partner.getNeighborhood();
    }

}
