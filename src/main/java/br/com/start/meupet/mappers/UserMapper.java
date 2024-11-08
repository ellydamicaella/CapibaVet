package br.com.start.meupet.mappers;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;


public final class UserMapper {

    public static UserResponseDTO userToResponseDTO(User user) {
        return new UserResponseDTO(user);
    }

    public static User userRequestToUser(UserRequestDTO user) {
        return new User(user.getName(), new Email(user.getEmail()), user.getPassword(), new PhoneNumber(user.getPhoneNumber()));
    }
}
