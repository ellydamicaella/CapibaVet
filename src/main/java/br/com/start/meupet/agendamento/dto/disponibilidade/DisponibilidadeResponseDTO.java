package br.com.start.meupet.agendamento.dto.disponibilidade;


import java.time.DayOfWeek;
import java.time.LocalTime;

public record DisponibilidadeResponseDTO(Long id, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
}
