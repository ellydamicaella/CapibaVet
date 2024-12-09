package br.com.start.meupet.agendamento.facade;

import br.com.start.meupet.agendamento.dto.disponibilidade.DisponibilidadeRequestDTO;
import br.com.start.meupet.agendamento.dto.disponibilidade.PartnerDisponibilidadeDTO;
import br.com.start.meupet.agendamento.usecase.disponibilidade.AddAvailabilityToPartnerUseCase;
import br.com.start.meupet.agendamento.usecase.disponibilidade.DeletaDisponibilidadeUseCase;
import br.com.start.meupet.agendamento.usecase.disponibilidade.GetPartnerAndAvailabilitiesUseCase;
import br.com.start.meupet.agendamento.usecase.disponibilidade.ListAllPartnersAndAvailabilitiesUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DisponibilidadeFacade {

    private final ListAllPartnersAndAvailabilitiesUseCase listAllPartnersAndAvailabilitiesUseCase;
    private final GetPartnerAndAvailabilitiesUseCase getPartnerAndAvailabilitiesUseCase;
    private final AddAvailabilityToPartnerUseCase addAvailabilityToPartnerUseCase;
    private final DeletaDisponibilidadeUseCase deletaDisponibilidadeUseCase;

    public DisponibilidadeFacade(ListAllPartnersAndAvailabilitiesUseCase listAllPartnersAndAvailabilitiesUseCase, GetPartnerAndAvailabilitiesUseCase getPartnerAndAvailabilitiesUseCase, AddAvailabilityToPartnerUseCase addAvailabilityToPartnerUseCase, DeletaDisponibilidadeUseCase deletaDisponibilidadeUseCase) {
        this.listAllPartnersAndAvailabilitiesUseCase = listAllPartnersAndAvailabilitiesUseCase;
        this.getPartnerAndAvailabilitiesUseCase = getPartnerAndAvailabilitiesUseCase;
        this.addAvailabilityToPartnerUseCase = addAvailabilityToPartnerUseCase;
        this.deletaDisponibilidadeUseCase = deletaDisponibilidadeUseCase;
    }

    public List<PartnerDisponibilidadeDTO> listaTodosParceirosESuasDisponibilidades() {
        return listAllPartnersAndAvailabilitiesUseCase.execute();
    }

    public PartnerDisponibilidadeDTO listaParceiroESuasDisponibilidades(UUID id) {
        return getPartnerAndAvailabilitiesUseCase.execute(id);
    }

    public void adicionaDisponibilidadeAoParceiro(UUID partnerId, DisponibilidadeRequestDTO disponibilidadeRequest) {
        addAvailabilityToPartnerUseCase.execute(partnerId, disponibilidadeRequest);
    }

    public void deletaDisponibilidadeDoParceiro(UUID partnerId, Long disponibilidadeId) {
        deletaDisponibilidadeUseCase.execute(partnerId, disponibilidadeId);
    }
}