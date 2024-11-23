package br.com.start.meupet.common.utils;

import java.security.SecureRandom;

public final class GeneratePasswordRecoveryToken {

    public static String execute() {
        int length = 8;
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(secureRandom.nextInt(10)); // Gera um número entre 0 e 9
        }

        return code.toString();
    }
}
