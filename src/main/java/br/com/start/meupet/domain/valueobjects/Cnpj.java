package br.com.start.meupet.domain.valueobjects;

import br.com.start.meupet.domain.interfaces.PersonalRegistration;
import br.com.start.meupet.exceptions.ProblemDetailsException;
import jakarta.persistence.Embeddable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@Embeddable
public final class Cnpj extends PersonalRegistration {

    private static final Logger log = LoggerFactory.getLogger(Cnpj.class);
    private final String cnpj;

    public Cnpj() {
        this.cnpj = null; // O valor padrão para inicialização pelo JPA
    }

    public Cnpj(String cnpj) {
        if (!isValidPersonalRegistration(cnpj)) {
            log.error("Formato do cnpj inválido: {}", cnpj);
            throw new ProblemDetailsException("CNPJ inválido", "Formato do cnpj inválido", HttpStatus.BAD_REQUEST);
        }
        this.cnpj = cnpj;
    }

    // Método para validar o CNPJ
    @Override
    protected boolean isValidPersonalRegistration(String personalRegistration) {
        // Remove caracteres não numéricos
        String cleanedCNPJ = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ tem 14 dígitos
        if (cleanedCNPJ.length() != 14) {
            return false;
        }

        // Cálculo dos dígitos verificadores
        return calculateDV(cleanedCNPJ);
    }

    // Método para calcular os dígitos verificadores
    @Override
    protected boolean calculateDV(String cnpj) {
        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        // Primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weight1[i];
        }
        int remainder = sum % 11;
        int dv1 = remainder < 2 ? 0 : 11 - remainder;

        // Verifica o primeiro dígito verificador
        if (dv1 != Character.getNumericValue(cnpj.charAt(12))) {
            return false;
        }

        // Segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weight2[i];
        }
        remainder = sum % 11;
        int dv2 = remainder < 2 ? 0 : 11 - remainder;

        // Verifica o segundo dígito verificador
        return dv2 != Character.getNumericValue(cnpj.charAt(13));
    }

    @Override
    public String toString() {
        return cnpj;
    }
}
