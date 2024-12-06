package br.com.start.meupet.partner.dto;

import br.com.start.meupet.agendamento.dto.disponibilidade.DisponibilidadeResponseDTO;
import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoResponseDTO;
import br.com.start.meupet.auth.dto.AuthenticableDTO;
import br.com.start.meupet.partner.model.Partner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDTO extends AuthenticableDTO {

    private UUID id;

    private String name;

    private String email;

    private String document;

    private String documentType;

    private String phoneNumber;

    private String streetAndNumber;

    private String neighborhood;

    private List<ServicoPrestadoResponseDTO> services;

    private List<DisponibilidadeResponseDTO> disponibilidades;


    public PartnerDTO(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.email = partner.getEmail().toString();
        this.document = partner.getPersonalRegistration().getDocument();
        this.documentType = partner.getPersonalRegistration().getDocumentType().toString();
        this.phoneNumber = partner.getPhoneNumber().toString();
        this.streetAndNumber = partner.getStreetAndNumber();
        this.neighborhood = partner.getNeighborhood();
        this.services = partner.getServicoPrestados().stream()
                .map(servico -> new ServicoPrestadoResponseDTO(servico.getId(), servico.getName().getServicoType(), servico.getPrice()))
                .collect(Collectors.toList());
        this.disponibilidades = partner.getDisponibilidades().stream()
                .map(disponibilidade -> new DisponibilidadeResponseDTO(disponibilidade.getStartTime(), disponibilidade.getEndTime()))
                .collect(Collectors.toList());
    }

}
