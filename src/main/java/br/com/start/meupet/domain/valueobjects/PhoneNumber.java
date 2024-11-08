package br.com.start.meupet.domain.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public final class PhoneNumber {

    private final String phoneNumber;

    // Regex para validação de telefone
    private static final String TELEFONE_REGEX = "^(\\(\\d{2}\\) \\d{2} \\d{5}-\\d{4}|\\(\\d{2}\\) \\d{2} \\d{4}-\\d{4}|\\(\\d{2}\\) \\d{2} \\d{11}|\\(\\d{2}\\) \\d{2} \\d{10})$";
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(TELEFONE_REGEX);

    public PhoneNumber() {
        this.phoneNumber = null; // O valor padrão para inicialização pelo JPA
    }

    public PhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Telefone inválido: " + phoneNumber);
        }
        this.phoneNumber = phoneNumber;
    }

    // Método para validar o telefone
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && TELEFONE_PATTERN.matcher(phoneNumber).matches();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return phoneNumber;
    }
}
