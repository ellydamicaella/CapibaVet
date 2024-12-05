package br.com.start.meupet.partner.dto;

import br.com.start.meupet.agendamento.dto.disponibilidade.DisponibilidadeRequestDTO;
import br.com.start.meupet.agendamento.dto.servico.ServicoPrestadoRequestDTO;

import java.util.List;

public record ServicoListAndAvailabilityRequestDTO(List<ServicoPrestadoRequestDTO> services, DisponibilidadeRequestDTO disponibilidade) {
}
