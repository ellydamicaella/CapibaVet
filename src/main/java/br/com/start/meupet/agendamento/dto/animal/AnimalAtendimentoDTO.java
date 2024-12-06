package br.com.start.meupet.agendamento.dto.animal;

import br.com.start.meupet.agendamento.model.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnimalAtendimentoDTO {
    private Long id;
    private String name;
    private String age;
    private String history;
//    private String porte;
    private String type;
    private Character sexo;

    public AnimalAtendimentoDTO(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.age = animal.getAge();
        this.history = animal.getHistory();
//        this.porte = animal.getPorte().getAnimalPorte();
        this.type = animal.getType().getAnimalType();
        this.sexo = animal.getSexo().getAnimalSexo();
    }

}
