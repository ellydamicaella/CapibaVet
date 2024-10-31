package br.com.start.meupet.enums;

public enum AnimalType {
    GATO("Gato"),
    CACHORRO("Cachorro");

    @SuppressWarnings("unused")
    private final String animalType;

    private AnimalType(String animalType) {
        this.animalType = animalType;
    }
}
