package br.com.start.meupet.domain.valueobjects;

import br.com.start.meupet.exceptions.ProblemDetailsException;
import jakarta.persistence.Embeddable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

@Embeddable
public final class Email {

    private static final Logger log = LoggerFactory.getLogger(Email.class);

    private final String email;

    // Regex para validação de email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Email() {
        this.email = null; // O valor padrão para inicialização pelo JPA
    }

    public Email(String email) {
        if (!isValidEmail(email)) {
            log.error("Formato do email inválido: {}", email);
            throw new ProblemDetailsException("Email inválido", "Formato do email inválido", HttpStatus.BAD_REQUEST);
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
