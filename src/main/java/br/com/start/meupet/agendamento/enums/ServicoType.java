package br.com.start.meupet.agendamento.enums;

import lombok.Getter;

@Getter
public enum ServicoType {
    CASTRACAO("Castração"),
    VACINAS("Vacinas"),
    PETSHOP("Pet Shop"),
    TOSABANHO("Tosa e Banho"),
    EXAMES("Exames"),
    CIRURGIAS("Cirurgias"),
    EMERGENCIAS("Emergências"),
    NUTRICIONISTA("Nutricionista");

    private final String servicoType;

    ServicoType(String servicoType) {
        this.servicoType = servicoType;
    }
    public boolean equalsIgnoreCase(String name) {
        return name.equalsIgnoreCase(servicoType);
    }
}
