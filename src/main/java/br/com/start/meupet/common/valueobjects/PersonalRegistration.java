package br.com.start.meupet.common.service.utils;

import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Embeddable
@Getter
public class PersonalRegistration {

    @Column(name = "document", nullable = false, unique = true)
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DocumentType type;

    public enum DocumentType {
        CPF, CNPJ
    }

    public PersonalRegistration() {}

    public PersonalRegistration(String document, DocumentType type) {
        if (type == DocumentType.CPF && !DocumentValidator.isValidCPF(document)) {
            throw new ProblemDetailsException("CPF inválido", "Formado do cpf inválido", HttpStatus.BAD_REQUEST);
        }
        if (type == DocumentType.CNPJ && !DocumentValidator.isValidCNPJ(document)) {
            throw new ProblemDetailsException("CNPJ inválido", "Formado do cnpj inválido", HttpStatus.BAD_REQUEST);
        }
        this.document = document;
        this.type = type;
    }

}