package br.com.start.meupet.enums;

public enum AnimalPorte {

    GRANDE("Grande"),
    MEDIO("Medio"),
    PEQUENO("Pequeno");

    private final String animalPorte;

    private AnimalPorte(String animalPorte) {
        this.animalPorte = animalPorte;
    }
}
