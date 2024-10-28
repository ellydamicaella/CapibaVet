package br.com.projeto.meupet.enums;

public enum AnimalPorte {

    GRANDE("Grande"),
    MEDIO("Medio"),
    PEQUENO("Pequeno");

    @SuppressWarnings("unused")
    private final String animalPorte;

    private AnimalPorte(String animalPorte) {
        this.animalPorte = animalPorte;
    }
}
