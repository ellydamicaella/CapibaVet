package br.com.start.meupet.agendamento.controller;

import br.com.start.meupet.agendamento.dto.DisponibilidadeRequestDTO;
import br.com.start.meupet.agendamento.model.Disponibilidade;
import br.com.start.meupet.agendamento.service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    //usado pelas clinicas para mostrar os dias, e horarios disponiveis para atendimento
    @PostMapping("/partner/{partnerId}/disponibilidade")
    public ResponseEntity<List<Disponibilidade>> adicionaDisponibilidade(@PathVariable UUID partnerId, @RequestBody DisponibilidadeRequestDTO disponibilidadeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.saveDisponibilidades(partnerId, disponibilidadeRequest));
    }
}
