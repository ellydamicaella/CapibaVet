package br.com.start.meupet.agendamento.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

public record DisponibilidadeRequestDTO(
        @NotEmpty List<@NotNull String> dias,
             @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime horaDeInicio,
             @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime horaDeFim) {
}
