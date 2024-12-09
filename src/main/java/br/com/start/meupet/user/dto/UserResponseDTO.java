package br.com.start.meupet.user.dto;

import br.com.start.meupet.auth.interfaces.AbstractAuthenticableResponseDTO;
import br.com.start.meupet.user.model.User;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDTO extends AbstractAuthenticableResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String document;
    private String documentType;
    private String birthDate;
    private int moedaCapiba;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail().toString();
        this.phoneNumber = user.getPhoneNumber().toString();
        this.document = user.getPersonalRegistration().getDocument();
        this.documentType = user.getPersonalRegistration().getDocumentType().toString();
        this.birthDate = String.valueOf(user.getDateOfBirth());
        this.moedaCapiba = user.getMoedaCapiba();
    }
}
