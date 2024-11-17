package br.com.start.meupet.user.service.mappers;

import java.time.LocalDateTime;

import br.com.start.meupet.user.model.User;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.dto.UserRequestDTO;
import br.com.start.meupet.user.dto.UserResponseDTO;


public final class UserMapper {

    public static UserResponseDTO userToResponseDTO(User user) {
        return new UserResponseDTO(user);
    }

    public static User userRequestToUser(UserRequestDTO user) {
        return new User(user.getName(), new Email(user.getEmail()), user.getPassword(), new PhoneNumber(user.getPhoneNumber()));
    }

    public static User userBeforeToNewUser(User oldUser, User newUser) {
        User user = new User(
                oldUser.getName(),
                newUser.getEmail(),
                newUser.getPassword(),
                newUser.getPhoneNumber()
        );
        user.setId(oldUser.getId());
        user.setMoedaCapiba(oldUser.getMoedaCapiba());
        user.setCreatedAt(oldUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
}
