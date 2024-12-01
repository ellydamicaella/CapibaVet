package br.com.start.meupet.agendamento.dto.disponibilidade;

import java.time.DayOfWeek;
import java.util.List;

public record DisponibilidadeRequestDTO(
        List<DayOfWeek> dias,
        String horaDeInicio,
        String horaDeFim) {
}
