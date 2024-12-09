package br.com.start.meupet.user.dto;

import br.com.start.meupet.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAtendimentoDTO {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    public UserAtendimentoDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail().toString();
        this.phone = user.getPhoneNumber().toString();
    }
}
