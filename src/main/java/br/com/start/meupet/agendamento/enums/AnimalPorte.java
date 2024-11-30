package br.com.start.meupet.agendamento.enums;

import lombok.Getter;

@Getter
public enum AnimalPorte {
    PEQUENO("Pequeno"),
    MEDIO("Medio"),
    GRANDE("Grande");

    private final String animalPorte;

    AnimalPorte(String animalPorte) {
        this.animalPorte = animalPorte;
    }
}
