package br.com.start.meupet.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SituationType {
    ATIVO("A", "Ativo"),
    INATIVO("I", "Inativo"),
    PENDENTE("P", "Pendente");

    private String codigo;
    private String descricao;

    private SituationType(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static SituationType doValue(String codigo) {
        return switch (codigo) {
            case "A" -> ATIVO;
            case "I" -> INATIVO;
            case "P" -> PENDENTE;
            default -> null;
        };
    }
}
