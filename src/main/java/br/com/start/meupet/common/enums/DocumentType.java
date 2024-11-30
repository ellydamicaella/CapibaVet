package br.com.start.meupet.common.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    CPF("CPF"),
    CNPJ("CNPJ");

    private final String documentType;

    DocumentType(String documentType) {
        this.documentType = documentType;
    }
}
