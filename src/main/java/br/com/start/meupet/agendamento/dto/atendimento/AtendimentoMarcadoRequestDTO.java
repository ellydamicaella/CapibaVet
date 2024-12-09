package br.com.start.meupet.agendamento.dto.atendimento;

import br.com.start.meupet.agendamento.dto.animal.AnimalRequestDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AtendimentoMarcadoRequestDTO(
        UUID partnerId,
       Long serviceId,
       UUID userId,
        AnimalRequestDTO animal,
       LocalDate appointmentDate,
       LocalTime startTime,
       LocalTime endTime
) {
}
