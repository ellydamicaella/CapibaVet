package br.com.start.meupet.domain.interfaces;

public abstract class PersonalRegistration {
    private String personalRegistration;

    protected abstract boolean isValidPersonalRegistration(String personalRegistration);

    protected abstract boolean calculateDV(String personalRegistration);
}
