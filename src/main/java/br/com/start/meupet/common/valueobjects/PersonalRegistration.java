package br.com.start.meupet.common.valueobjects;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.common.utils.DocumentValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Embeddable
@Getter
public class PersonalRegistration {

    @Column(name = "document", nullable = false)
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentType", nullable = false)
    private DocumentType documentType;


    public PersonalRegistration() {}

    public PersonalRegistration(String document, DocumentType documentType) {
        if (documentType == DocumentType.CPF && !DocumentValidator.isValidCPF(document)) {
            throw new ProblemDetailsException("CPF inválido", "Formado do cpf inválido", HttpStatus.BAD_REQUEST);
        }
        if (documentType == DocumentType.CNPJ && !DocumentValidator.isValidCNPJ(document)) {
            throw new ProblemDetailsException("CNPJ inválido", "Formado do cnpj inválido", HttpStatus.BAD_REQUEST);
        }
        this.document = document;
        this.documentType = documentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalRegistration that = (PersonalRegistration) o;
        return Objects.equals(document, that.document) && documentType == that.documentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, documentType);
    }

}