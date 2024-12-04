package br.com.start.meupet.agendamento.enums;

import lombok.Getter;

@Getter
public enum ServicoType {
    CASTRACAO("Castração"),
    VACINAS("Vacinas"),
    PETSHOP("Pet Shop"),
    TOSA_E_BANHO("Tosa e Banho"),
    EXAMES("Exames"),
    CIRURGIAS("Cirurgias"),
    EMERGÊNCIAS("Emergências"),
    NUTRICIONISTA("Nutricionista"),
    CUIDADOS_GERIATRICOS("Cuidados Geriátricos");

    private final String servicoType;

    ServicoType(String servicoType) {
        this.servicoType = servicoType;
    }
}
