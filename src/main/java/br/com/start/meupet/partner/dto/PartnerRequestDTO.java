package br.com.start.meupet.dto;


import br.com.start.meupet.domain.interfaces.PersonalRegistration;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestDTO {

    @Valid
    private String name;
    @Valid
    private String email;
    @Valid
    private String personalRegistration;
    @Valid
    private String password;
    @Valid
    private String phoneNumber;
}
