package br.com.start.meupet.agendamento.dto.atendimento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AtendimentoMarcadoRequestDTO(
        UUID partnerId,
       Integer serviceId,
       UUID userId,
       Long animalId,
       LocalDate appointmentDate,
       LocalTime startTime,
       LocalTime endTime
) {
}
