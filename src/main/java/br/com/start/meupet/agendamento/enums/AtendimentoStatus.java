package br.com.start.meupet.agendamento.enums;

public enum AtendimentoStatus {
    PENDENTE("PENDENTE"),
    CONFIRMADO("CONFIRMADO"),
    CANCELADO("CANCELADO");

    private final String atendimentoStatus;

    AtendimentoStatus(String atendimentoStatus) {
        this.atendimentoStatus = atendimentoStatus;
    }

    public String getAtendimentoStatus() {
        return atendimentoStatus;
    }
}
