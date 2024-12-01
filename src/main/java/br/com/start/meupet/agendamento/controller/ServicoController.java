package br.com.start.meupet.agendamento.controller;

import br.com.start.meupet.agendamento.dto.servico.PartnerServicoDTO;
import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoRequestDTO;
import br.com.start.meupet.agendamento.facade.ServicoFacade;
import br.com.start.meupet.auth.dto.StatusResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamento/partner/servico")
@CrossOrigin
public class ServicoController {

    private final ServicoFacade servicoFacade;

    public ServicoController(ServicoFacade servicoFacade) {
        this.servicoFacade = servicoFacade;
    }

    @GetMapping
    public ResponseEntity<List<PartnerServicoDTO>> listAll() {
        return ResponseEntity.ok().body(servicoFacade.listaParceirosESeusServicos());
    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerServicoDTO> listaParceiroESeuServico(@PathVariable UUID partnerId) {
        return ResponseEntity.ok().body(servicoFacade.listaParceiroESeusServicos(partnerId));
    }

    @PostMapping("/{partnerId}")
    public ResponseEntity<StatusResponseDTO> adicionaServicoAoParceiro(@PathVariable UUID partnerId, @RequestBody ServicoPrestadoRequestDTO servicoPrestado) {
        servicoFacade.adidionaServicoAoParceiro(partnerId, servicoPrestado);
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Servico adicionado ao parceiro com sucesso!"));
    }

    @PutMapping("/{partnerId}/{servicoId}")
    public ResponseEntity<StatusResponseDTO> atualizarServico(
            @PathVariable UUID partnerId,
            @PathVariable Long servicoId,
            @RequestBody ServicoPrestadoRequestDTO servicoPrestadoDTO) {
        servicoFacade.atualizaServico(partnerId, servicoId, servicoPrestadoDTO);
        return ResponseEntity.ok().body(new StatusResponseDTO("success", "Servico do parceiro alterado com sucesso!"));
    }

    @DeleteMapping("/{partnerId}/{servicoId}")
    public ResponseEntity<StatusResponseDTO> deleteServico(@PathVariable UUID partnerId, @PathVariable Long servicoId) {
        servicoFacade.deletaServico(partnerId, servicoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
