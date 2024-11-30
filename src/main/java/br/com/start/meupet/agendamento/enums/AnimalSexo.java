package br.com.start.meupet.agendamento.enums;

public enum AnimalSexo {
    M('M'),
    F('F');

    private final Character animalSexo;

    AnimalSexo(Character animalSexo) {
        this.animalSexo = animalSexo;
    }

    public Character getAnimalSexo() {
        return animalSexo;
    }
}
