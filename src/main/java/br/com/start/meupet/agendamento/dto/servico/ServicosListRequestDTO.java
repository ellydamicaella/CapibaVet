package br.com.start.meupet.agendamento.dto.servico;

import java.util.List;

public record ServicosListRequestDTO(List<ServicoPrestadoRequestDTO> services) {
}
