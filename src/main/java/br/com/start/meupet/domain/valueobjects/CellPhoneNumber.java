package br.com.start.meupet.domain.valueobjects;

import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

@Embeddable
public final class CellPhoneNumber {

    private final String cellPhoneNumber;

    // Regex para validação de telefone
    private static final String TELEFONE_REGEX = "^(\\(\\d{2}\\) \\d{2} \\d{5}-\\d{4}|\\(\\d{2}\\) \\d{2} \\d{4}-\\d{4}|\\(\\d{2}\\) \\d{2} \\d{11}|\\(\\d{2}\\) \\d{2} \\d{10})$";
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(TELEFONE_REGEX);

    protected CellPhoneNumber() {
        this.cellPhoneNumber = null; // O valor padrão para inicialização pelo JPA
    }

    public CellPhoneNumber(String cellPhoneNumber) {
        if (!isValidCellPhoneNumber(cellPhoneNumber)) {
            throw new IllegalArgumentException("Telefone inválido: " + cellPhoneNumber);
        }
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Método para validar o telefone
    private boolean isValidCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null && TELEFONE_PATTERN.matcher(cellPhoneNumber).matches();
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    @Override
    public String toString() {
        return cellPhoneNumber;
    }
}
