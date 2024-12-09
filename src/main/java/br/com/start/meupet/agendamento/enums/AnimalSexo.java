package br.com.start.meupet.agendamento.enums;

import lombok.Getter;

@Getter
public enum AnimalSexo {
    M('M'),
    F('F');

    private final Character animalSexo;

    AnimalSexo(Character animalSexo) {
        this.animalSexo = animalSexo;
    }
}
