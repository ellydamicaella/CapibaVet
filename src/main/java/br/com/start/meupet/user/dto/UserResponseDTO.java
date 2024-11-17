package br.com.start.meupet.dto;

import br.com.start.meupet.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

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
