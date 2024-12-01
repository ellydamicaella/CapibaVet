package br.com.start.meupet.agendamento.dto.disponibilidade;

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
public class PartnerDisponibilidadeDTO {
    private UUID id;

    private String name;

    private String email;

    private String document;

    private String documentType;

    private String phoneNumber;

    private List<DisponibilidadeResponseDTO> disponibilidades;

    public PartnerDisponibilidadeDTO(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.email = partner.getEmail().toString();
        this.document = partner.getPersonalRegistration().getDocument();
        this.documentType = partner.getPersonalRegistration().getDocumentType().toString();
        this.phoneNumber = partner.getPhoneNumber().toString();
        this.disponibilidades = partner.getDisponibilidades().stream()
                .map(servico -> new DisponibilidadeResponseDTO(
                        servico.getId(),
                        servico.getDayOfWeek(),
                        servico.getStartTime(),
                        servico.getEndTime()
                )).collect(Collectors.toList());
    }
}
