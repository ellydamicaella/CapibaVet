package br.com.start.meupet.agendamento.dto.servico;

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
public class PartnerServicoDTO {
    private UUID id;

    private String name;

    private String email;

    private String document;

    private String documentType;

    private String phoneNumber;

    private List<ServicoPrestadoResponseDTO> servicoPrestados;

    public PartnerServicoDTO(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.email = partner.getEmail().toString();
        this.document = partner.getPersonalRegistration().getDocument();
        this.documentType = partner.getPersonalRegistration().getDocumentType().toString();
        this.phoneNumber = partner.getPhoneNumber().toString();
        this.servicoPrestados = partner.getServicoPrestados().stream()
                .map(servico -> new ServicoPrestadoResponseDTO(
                        servico.getId(),
                        servico.getName(),
                        servico.getDescription(),
                        servico.getPrice()
                )).collect(Collectors.toList());
    }
}
