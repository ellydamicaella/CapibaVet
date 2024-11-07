package br.com.start.meupet.domain.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public final class Email {

    private final String email;

    // Regex para validação de email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Email() {
        this.email = null; // O valor padrão para inicialização pelo JPA
    }

    public Email(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
        this.email = email;
    }

    // Método para validar o email
    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return email;
    }
}
