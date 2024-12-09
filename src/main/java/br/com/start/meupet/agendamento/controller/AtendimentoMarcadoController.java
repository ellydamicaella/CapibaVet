package br.com.start.meupet.agendamento.controller;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoRequestDTO;
import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoStatusDTO;
import br.com.start.meupet.agendamento.enums.AtendimentoStatus;
import br.com.start.meupet.agendamento.facade.AtendimentoMarcadoFacade;
import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.agendamento.repository.AtendimentoMarcadoRepository;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/agendamento/atendimento")
@CrossOrigin
@Slf4j
public class AtendimentoMarcadoController {

    private final AtendimentoMarcadoFacade atendimentoMarcadoFacade;
    private final PartnerRepository partnerRepository;
    private final AtendimentoMarcadoRepository atendimentoMarcadoRepository;

    public AtendimentoMarcadoController(AtendimentoMarcadoFacade atendimentoMarcadoFacade, PartnerRepository partnerRepository, AtendimentoMarcadoRepository atendimentoMarcadoRepository) {
        this.atendimentoMarcadoFacade = atendimentoMarcadoFacade;
        this.partnerRepository = partnerRepository;
        this.atendimentoMarcadoRepository = atendimentoMarcadoRepository;
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoMarcadoDTO>> listaAtendimentosMarcados() {
        return ResponseEntity.ok().body(atendimentoMarcadoFacade.listaTodosAtendimentosMarcado());
    }

    @GetMapping("/partner/{partnerId}")
    public ResponseEntity<List<AtendimentoMarcadoDTO>> listaAtendimentoMarcadoParaClinica(@PathVariable UUID partnerId) {
        Partner partner = partnerRepository.findById(partnerId).orElseThrow(() -> new EntityNotFoundException("Partner not found"));

        // Busca os atendimentos marcados associados ao usuário
        List<AtendimentoMarcado> atendimentosMarcados = atendimentoMarcadoRepository.findByPartner(partner);

        // Converte os atendimentos marcados para DTOs
        List<AtendimentoMarcadoDTO> atendimentosDTO = atendimentosMarcados.stream()
                .map(atendimento -> new AtendimentoMarcadoDTO(
                        atendimento,
                        atendimento.getPartner(),
                        atendimento.getUser(),
                        atendimento.getServicoPrestado(),
                        atendimento.getAnimal()
                ))
                .toList();
        return ResponseEntity.ok().body(atendimentosDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AtendimentoMarcadoDTO>> listaAtendimentoMarcado(@PathVariable UUID userId) {
        return ResponseEntity.ok().body(atendimentoMarcadoFacade.listaAtendimentoUsuario(userId));
    }

    @PostMapping
    public ResponseEntity<StatusResponseDTO> adicionaAtendimentoMarcado(@RequestBody AtendimentoMarcadoRequestDTO request) {
        atendimentoMarcadoFacade.adicionaAtendimentoMarcado(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new StatusResponseDTO("success", "Atendimento marcado com sucesso!"));
    }

    @PutMapping("/{partnerId}/{atendimentoMarcadoId}")
    public ResponseEntity<StatusResponseDTO> alteraStatusDeAtendimentoMarcado(@PathVariable UUID partnerId, @PathVariable Long atendimentoMarcadoId, @RequestBody AtendimentoStatusDTO status) {
        log.info("{}", status.status());
        atendimentoMarcadoFacade.atualizaStatusAtendimentoMarcado(partnerId, atendimentoMarcadoId, status);
        return ResponseEntity.ok(new StatusResponseDTO("success", "Status do atendimento atualizado com sucesso."));
    }

    @DeleteMapping("/{partnerId}/{atendimentoMarcadoId}")
    public ResponseEntity<Void> deletaAtendimentoMarcado(@PathVariable UUID partnerId, @PathVariable Long atendimentoMarcadoId) {
        return ResponseEntity.noContent().build();
    }
}
