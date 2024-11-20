package br.com.start.meupet.common.interfaces;

import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;

import java.util.UUID;

public abstract class AuthenticableResponseDTO {
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;
}
