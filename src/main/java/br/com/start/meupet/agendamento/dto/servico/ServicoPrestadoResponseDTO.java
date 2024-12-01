package br.com.start.meupet.agendamento.dto.servico;

import java.math.BigDecimal;

public record ServicoPrestadoResponseDTO(Long id, String name, String description, BigDecimal price) {
}
