package br.com.start.meupet.mappers;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;

import java.time.LocalDateTime;


public final class UserMapper {

    public static UserResponseDTO userToResponseDTO(User user) {
        return new UserResponseDTO(user);
    }

    public static User userRequestToUser(UserRequestDTO user) {
        return new User(user.getName(), new Email(user.getEmail()), user.getPassword(), new PhoneNumber(user.getPhoneNumber()));
    }

    public static User userBeforeToNewUser(User oldUser, User newUser) {
        User user = new User();
        user.setId((long) oldUser.getId());
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setMoedaCapiba(oldUser.getMoedaCapiba());
        user.setPassword(newUser.getPassword());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setCreatedAt(oldUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        user.setSituationType(oldUser.getSituationType());
        return user;
    }
}
