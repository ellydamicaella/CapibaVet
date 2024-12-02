package br.com.start.meupet.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private String name;
    private String socialName;
    private String email;
    private String phoneNumber;
    private String document;
    private String documentType;
    private String dateOfBirth;
}
