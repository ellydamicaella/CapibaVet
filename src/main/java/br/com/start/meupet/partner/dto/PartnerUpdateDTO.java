package br.com.start.meupet.partner.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerUpdateDTO {
    private String name;
    private String phoneNumber;
    private String streetAndNumber;
    private String neighborhood;
}
