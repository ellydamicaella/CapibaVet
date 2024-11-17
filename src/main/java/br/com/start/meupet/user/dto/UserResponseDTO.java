package br.com.start.meupet.user.dto;

import br.com.start.meupet.common.interfaces.AuthenticableResponseDTO;
import br.com.start.meupet.user.model.User;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDTO extends AuthenticableResponseDTO {

    private UUID id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private int moedaCapiba;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail().toString();
        this.phoneNumber = user.getPhoneNumber().toString();
        this.moedaCapiba = user.getMoedaCapiba();
    }
}
