package br.com.start.meupet.agendamento.enums;

import lombok.Getter;

@Getter
public enum AnimalType {
    CACHORRO("Cachorro"),
    GATO("Gato");

    private final String animalType;

    AnimalType(String animalType) {
        this.animalType = animalType;
    }
}