package br.com.start.meupet.common.templates;

import lombok.Getter;

@Getter
public enum TemplateNameEnum {
    CHANGE_PASSWORD("changePassword.html"),
    CONFIRM_ACCOUNT("confirmacaoConta.html"),
    EMAIL_CONFIRM_ACCOUNT("emailConfirmAccountTemplate.html"),
    EMAIL_PASSWORD_RECOVERY("emailPasswordRecoveryTemplate.html");

    private final String fileName;

    TemplateNameEnum(String fileName) {
        this.fileName = fileName;
    }

}
