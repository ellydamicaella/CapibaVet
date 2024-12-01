package br.com.start.meupet.agendamento.dto.animal;

import br.com.start.meupet.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnimalDTO {

    private UUID id;

    private String name;

    private String email;

    private String document;

    private String documentType;

    private String phoneNumber;

    private List<AnimalResponseDTO> animais;

    public UserAnimalDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail().toString();
        this.document = user.getPersonalRegistration().getDocument();
        this.documentType = user.getPersonalRegistration().getDocumentType().toString();
        this.phoneNumber = user.getPhoneNumber().toString();
        this.animais = user.getAnimals().stream()
                .map(animal -> new AnimalResponseDTO(
                        animal.getId(),
                        animal.getName(),
                        animal.getPorte(),
                        animal.getType(),
                        animal.getSexo()
                )).collect(Collectors.toList());
    }
}
