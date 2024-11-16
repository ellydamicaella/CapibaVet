package br.com.start.meupet.domain.valueobjects;

import br.com.start.meupet.domain.interfaces.PersonalRegistration;
import br.com.start.meupet.exceptions.ProblemDetailsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class Cpf extends PersonalRegistration {

    private static final Logger log = LoggerFactory.getLogger(Cpf.class);
    private final String cpf;

    public Cpf() {
        this.cpf = null; // Valor padrão para inicialização pelo JPA
    }

    public Cpf(String cpf) {
        if (!isValidPersonalRegistration(cpf)) {
            log.error("Formato do email inválido: {}", cpf);
            throw new ProblemDetailsException("CPF inválido", "Formato do cpf inválido", HttpStatus.BAD_REQUEST);
        }
        this.cpf = cpf;
    }

    // Método para validar o CPF
    @Override
    protected boolean isValidPersonalRegistration(String cpf) {
        // Remove caracteres não numéricos
        String cleanedCPF = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cleanedCPF.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: "11111111111" é inválido)
        if (cleanedCPF.chars().distinct().count() == 1) {
            return false;
        }

        // Cálculo dos dígitos verificadores
        return calculateDV(cleanedCPF);
    }

    // Método para calcular os dígitos verificadores
    @Override
    protected boolean calculateDV(String cpf) {
        int[] weight1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        // Primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weight1[i];
        }
        int remainder = sum % 11;
        int dv1 = remainder < 2 ? 0 : 11 - remainder;

        // Verifica o primeiro dígito verificador
        if (dv1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weight2[i];
        }
        remainder = sum % 11;
        int dv2 = remainder < 2 ? 0 : 11 - remainder;

        // Verifica o segundo dígito verificador
        return dv2 != Character.getNumericValue(cpf.charAt(10));
    }

    @Override
    public String toString() {
        return cpf;
    }
}
