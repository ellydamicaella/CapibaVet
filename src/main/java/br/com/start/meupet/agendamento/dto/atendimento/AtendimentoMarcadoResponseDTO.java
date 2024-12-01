package br.com.start.meupet.agendamento.dto.atendimento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AtendimentoMarcadoResponseDTO(
        Long id,
        LocalDate dataAgendamento,
        LocalTime horaInicio,
        LocalTime horaFim,
        UUID partnerId,
        String partnerName,
        String partnerEmail,
        String partnerPhone,
        UUID userId,
        String userName,
        String userEmail,
        String userPhone,
        Long servicoId,
        String servicoName,
        Long animalId,
        String animalName,
        String animalPorte,
        String animalType,
        char animalSexo
        ) {

}
