package br.com.start.meupet.agendamento.enums;

public enum AnimalType {
    CACHORRO("Cachorro"),
    GATO("Gato");

    private final String animalType;

    AnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalType() {
        return animalType;
    }
}