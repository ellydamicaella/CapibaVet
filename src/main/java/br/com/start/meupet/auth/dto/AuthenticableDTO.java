package br.com.start.meupet.auth.dto;

import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class AuthenticableDTO {
    private UUID id;
    private String name;
    private String socialName;
    private String email;
    private String phoneNumber;
    private String document;
    private String documentType;
    private String streetAndNumber;
    private String neighborhood;
    private String dateOfBirth;
    private Integer moedaCapiba;
    private List<ServicoPrestadoResponseDTO> services;
}
