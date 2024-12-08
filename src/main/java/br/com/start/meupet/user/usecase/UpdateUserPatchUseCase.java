package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.exceptions.EntityNotFoundException;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.dto.UserUpdateDTO;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class UpdateUserPatchUseCase {

    private final UserRepository userRepository;

    public UpdateUserPatchUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId, UserUpdateDTO userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com ID: " + userId));

        // Atualiza apenas os campos não nulos do DTO
        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        }
        if (userRequest.getSocialName() != null) {
            user.setSocialName(userRequest.getSocialName());
        }
        if (userRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(new PhoneNumber(userRequest.getPhoneNumber()));
        }
        if (userRequest.getDateOfBirth() != null) {
            user.setDateOfBirth(BirthDayUtils.convertToDate(userRequest.getDateOfBirth()));
        }
        userRepository.save(user); // Persistindo as alterações no banco
        log.info("Requisicao PATCH: updateUser - {}", user);
    }
}
