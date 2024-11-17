package br.com.start.meupet.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @Valid
    private String name;
    @Valid
    private String password;
    @Valid
    private String email;
    @Valid
    private String phoneNumber;
}
