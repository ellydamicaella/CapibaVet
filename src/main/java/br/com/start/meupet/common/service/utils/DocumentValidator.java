package br.com.start.meupet.common.service.utils;

public class DocumentValidator {
    public static boolean isValidCPF(String cpf) {
        // Implementação de validação para CPF
        return cpf != null && cpf.matches("\\d{11}");
    }

    public static boolean isValidCNPJ(String cnpj) {
        // Implementação de validação para CNPJ
        return cnpj != null && cnpj.matches("\\d{14}");
    }

}
