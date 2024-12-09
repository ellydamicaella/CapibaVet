package br.com.start.meupet.agendamento.dto.animal;

import br.com.start.meupet.agendamento.enums.AnimalSexo;
import br.com.start.meupet.agendamento.enums.AnimalType;

public record AnimalResponseDTO(Long id, String name, String age, String history, AnimalType type, AnimalSexo sexo) {
}
