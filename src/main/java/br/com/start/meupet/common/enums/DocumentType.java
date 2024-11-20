package br.com.start.meupet.common.enums;

public enum DocumentType {
    CPF("CPF"),
    CNPJ("CNPJ");

    private final String documentType;

    private DocumentType(String documentType) {
        this.documentType = documentType;
    }
}
