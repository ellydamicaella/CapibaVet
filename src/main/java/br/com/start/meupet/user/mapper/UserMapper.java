package br.com.start.meupet.user.mapper;

import java.time.LocalDateTime;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
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
        return new User(
                user.getName(),
                user.getSocialName(),
                new Email(user.getEmail()),
                user.getPassword(),
                new PhoneNumber(user.getPhoneNumber()),
                new PersonalRegistration(user.getDocument(),
                        user.toDocumentType(user.getDocumentType())),
                BirthDayUtils.convertToDate(user.getBirthDate())
                );
    }

    public static User userBeforeToNewUser(User oldUser, User newUser) {
        User user = new User(
                newUser.getName(),
                newUser.getSocialName(),
                newUser.getEmail(),
                newUser.getPassword(),
                newUser.getPhoneNumber(),
                new PersonalRegistration(newUser.getPersonalRegistration().getDocument(), newUser.getPersonalRegistration().getDocumentType()),
                newUser.getDateOfBirth()
        );
        user.setId(oldUser.getId());
        user.setMoedaCapiba(oldUser.getMoedaCapiba());
        user.setCreatedAt(oldUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
}
