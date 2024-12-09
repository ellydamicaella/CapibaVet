package br.com.start.meupet.agendamento.dto;

import java.time.LocalTime;
import java.util.UUID;

public record AgendamentoRequestDTO(UUID partnerId, int serviceId, String dataDeAgendamento, LocalTime dataDeInicio) {
}
