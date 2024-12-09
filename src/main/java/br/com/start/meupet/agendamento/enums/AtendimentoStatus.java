package br.com.start.meupet.agendamento.enums;

import lombok.Getter;

@Getter
public enum AtendimentoStatus {
    PENDENTE("PENDENTE"),
    CONFIRMADO("CONFIRMADO"),
    CANCELADO("CANCELADO");

    private final String atendimentoStatus;

    AtendimentoStatus(String atendimentoStatus) {
        this.atendimentoStatus = atendimentoStatus;
    }
}
