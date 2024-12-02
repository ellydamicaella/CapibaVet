package br.com.start.meupet.partner.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerUpdateDTO {
    private String name;
    private String document;
    private String documentType;
    private String phoneNumber;
    private String street;
    private String neighborhood;
    private String city;
    private String CEP;
}
