package br.com.start.meupet.partner.dto;


import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestDTO {

    @Valid
    private String name;
    @Valid
    private String email;
    @Valid
    private String document;
    @Valid
    private String documentType;
    @Valid
    private String password;
    @Valid
    private String phoneNumber;

    public DocumentType toDocumentType(String documentType) {
        return DocumentType.valueOf(documentType.trim().toUpperCase());
    }
}
