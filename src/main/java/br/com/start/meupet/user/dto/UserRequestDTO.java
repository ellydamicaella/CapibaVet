package br.com.start.meupet.user.dto;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.user.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @Valid
    private String name;
    @Valid
    private String socialName;
    @Valid
    private String password;
    @Valid
    private String email;
    @Valid
    private String phoneNumber;
    @Valid
    private String document;
    @Valid
    private String documentType;
    @Valid
    private String birthDate;

    public DocumentType toDocumentType(String documentType) {
        return DocumentType.valueOf(documentType.trim().toUpperCase());
    }
}
