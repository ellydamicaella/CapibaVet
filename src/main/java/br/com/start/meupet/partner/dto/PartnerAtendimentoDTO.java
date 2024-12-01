package br.com.start.meupet.partner.dto;

import br.com.start.meupet.partner.model.Partner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerAtendimentoDTO {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    public PartnerAtendimentoDTO(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.email = partner.getEmail().toString();
        this.phone = partner.getPhoneNumber().toString();
    }
}
