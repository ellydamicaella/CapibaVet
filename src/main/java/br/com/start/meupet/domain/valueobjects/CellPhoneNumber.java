package br.com.start.meupet.domain.valueobjects;

import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

@Embeddable
public final class Telefone {

    private final String telefone;

    // Regex para validação de telefone
    private static final String TELEFONE_REGEX = "^(\\(\\d{2}\\) \\d{2} \\d{5}-\\d{4}|\\(\\d{2}\\) \\d{2} \\d{4}-\\d{4}|\\(\\d{2}\\) \\d{2} \\d{11}|\\(\\d{2}\\) \\d{2} \\d{10})$";
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(TELEFONE_REGEX);

    protected Telefone() {
        this.telefone = null; // O valor padrão para inicialização pelo JPA
    }

    public Telefone(String telefone) {
        if (!isValidTelefone(telefone)) {
            throw new IllegalArgumentException("Telefone inválido: " + telefone);
        }
        this.telefone = telefone;
    }

    // Método para validar o telefone
    private boolean isValidTelefone(String telefone) {
        return telefone != null && TELEFONE_PATTERN.matcher(telefone).matches();
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return telefone;
    }
}
