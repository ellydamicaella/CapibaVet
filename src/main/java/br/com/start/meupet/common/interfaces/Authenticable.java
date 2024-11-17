package br.com.start.meupet.domain.interfaces;

import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;

import java.util.UUID;

public interface Authenticable {

    UUID getId();

    String getName();

    Email getEmail();

    String getPassword();

    PhoneNumber getPhoneNumber();
}
