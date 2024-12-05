package br.com.start.meupet.agendamento.dto.disponibilidade;


import java.time.LocalTime;

public record DisponibilidadeResponseDTO(LocalTime openingHour, LocalTime closingHour) {
}
