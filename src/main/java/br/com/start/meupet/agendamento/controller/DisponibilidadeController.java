package br.com.start.meupet.agendamento.controller;

import br.com.start.meupet.agendamento.dto.disponibilidade.DisponibilidadeRequestDTO;
import br.com.start.meupet.agendamento.dto.disponibilidade.PartnerDisponibilidadeDTO;
import br.com.start.meupet.agendamento.facade.DisponibilidadeFacade;
import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.agendamento.repository.DisponibilidadeRepository;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/agendamento/partner/disponibilidade")
@CrossOrigin
public class DisponibilidadeController {

    private final PartnerRepository partnerRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeFacade disponibilidadeFacade;

    public DisponibilidadeController(PartnerRepository partnerRepository, DisponibilidadeRepository disponibilidadeRepository, DisponibilidadeFacade disponibilidadeFacade) {
        this.partnerRepository = partnerRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.disponibilidadeFacade = disponibilidadeFacade;
    }

    @GetMapping
    public ResponseEntity<List<PartnerDisponibilidadeDTO>> listaParceirosESuasDisponibilidades() {
        return ResponseEntity.ok().body(disponibilidadeFacade.listaTodosParceirosESuasDisponibilidades());
    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerDisponibilidadeDTO> listaParceiroESuaDisponibilidade(@PathVariable UUID partnerId) {
        return ResponseEntity.ok().body(disponibilidadeFacade.listaParceiroESuasDisponibilidades(partnerId));
    }

    @PostMapping("/{partnerId}")
    public ResponseEntity<StatusResponseDTO> adicionaDisponibilidadeAoParceiro(@PathVariable UUID partnerId, @RequestBody DisponibilidadeRequestDTO disponibilidadeRequest) {
        disponibilidadeFacade.adicionaDisponibilidadeAoParceiro(partnerId, disponibilidadeRequest);
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Disponibilidade adicionado ao parceiro com sucesso!"));
    }

    @DeleteMapping("/{partnerId}/{disponibilidadeId}")
    public ResponseEntity<StatusResponseDTO> deleteServico(@PathVariable UUID partnerId, @PathVariable Long disponibilidadeId) {
        Optional<Partner> partnerOpt = partnerRepository.findById(partnerId);
        if (partnerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusResponseDTO("error", "Parceiro nao encontrado"));
        }
        Partner partner = partnerOpt.get();
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(disponibilidadeId);
        //servico.setPartner(partner);
        disponibilidadeRepository.delete(disponibilidade);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
