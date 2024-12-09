package br.com.start.meupet.agendamento.controller;

import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoDTO;
import br.com.start.meupet.agendamento.dto.atendimento.AtendimentoMarcadoRequestDTO;
import br.com.start.meupet.agendamento.facade.AtendimentoMarcadoFacade;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
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

    public AtendimentoMarcadoController(AtendimentoMarcadoFacade atendimentoMarcadoFacade) {
        this.atendimentoMarcadoFacade = atendimentoMarcadoFacade;
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoMarcadoDTO>> listaAtendimentosMarcados() {
        return ResponseEntity.ok().body(atendimentoMarcadoFacade.listaTodosAtendimentosMarcado());
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
    public ResponseEntity<StatusResponseDTO> alteraStatusDeAtendimentoMarcado(@PathVariable UUID partnerId, @PathVariable Long atendimentoMarcadoId, @RequestParam String status) {
        atendimentoMarcadoFacade.atualizaStatusAtendimentoMarcado(partnerId, atendimentoMarcadoId, status);
        return ResponseEntity.ok(new StatusResponseDTO("success", "Status do atendimento atualizado com sucesso."));
    }

}