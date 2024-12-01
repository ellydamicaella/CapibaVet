package br.com.start.meupet.agendamento.dto.servico;

import java.math.BigDecimal;

public record ServicoPrestadoRequestDTO(String name, String description, BigDecimal price) {
}
