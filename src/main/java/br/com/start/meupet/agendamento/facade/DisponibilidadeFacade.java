package br.com.start.meupet.agendamento.facade;

import br.com.start.meupet.agendamento.dto.disponibilidade.PartnerDisponibilidadeDTO;
import br.com.start.meupet.agendamento.usecase.disponibilidade.GetPartnerAndAvailabilities;
import br.com.start.meupet.agendamento.usecase.disponibilidade.ListAllPartnersAndAvailabilities;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DisponibilidadeFacade {

    private final ListAllPartnersAndAvailabilities listAllPartnersAndAvailabilities;
    private final GetPartnerAndAvailabilities getPartnerAndAvailabilities;

    public DisponibilidadeFacade(ListAllPartnersAndAvailabilities listAllPartnersAndAvailabilities, GetPartnerAndAvailabilities getPartnerAndAvailabilities) {
        this.listAllPartnersAndAvailabilities = listAllPartnersAndAvailabilities;
        this.getPartnerAndAvailabilities = getPartnerAndAvailabilities;
    }

    public List<PartnerDisponibilidadeDTO> listaTodosParceirosESuasDisponibilidades() {
        return listAllPartnersAndAvailabilities.execute();
    }

    public PartnerDisponibilidadeDTO listaParceiroESuasDisponibilidades(UUID id) {
        return getPartnerAndAvailabilities.execute(id);
    }
}
