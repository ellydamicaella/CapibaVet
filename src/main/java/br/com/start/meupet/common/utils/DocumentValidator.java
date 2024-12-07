package br.com.start.meupet.common.utils;

public class DocumentValidator {
    public static boolean isValidCPF(String cpf) {
        // Implementação de validação para CPF
        String cpfPattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
        return cpf != null && cpf.matches(cpfPattern);
    }

    public static boolean isValidCNPJ(String cnpj) {
        // Implementação de validação para CNPJ
        String cnpjPattern = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}";
        return cnpj != null && cnpj.matches(cnpjPattern);
    }




}
