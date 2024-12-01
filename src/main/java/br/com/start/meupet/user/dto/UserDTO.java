package br.com.start.meupet.user.dto;

import br.com.start.meupet.auth.dto.AuthenticableDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AuthenticableDTO {

    private UUID id;
    private String name;
    private String socialName;
    private String email;
    private String phoneNumber;
    private String document;
    private String documentType;
    private String dateOfBirth;
    private Integer moedaCapiba;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.socialName = user.getSocialName();
        this.email = user.getEmail().toString();
        this.document = user.getPersonalRegistration().getDocument();
        this.documentType = user.getPersonalRegistration().getDocumentType().toString();
        this.phoneNumber = user.getPhoneNumber().toString();
        this.dateOfBirth = user.getDateOfBirth().toString();
        this.moedaCapiba = user.getMoedaCapiba();
    }
}
