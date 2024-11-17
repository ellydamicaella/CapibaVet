package br.com.start.meupet.common.interfaces;

import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;

import java.util.UUID;

public interface Authenticable {

    UUID getId();

    String getName();

    Email getEmail();

    String getPassword();

    PhoneNumber getPhoneNumber();
}
