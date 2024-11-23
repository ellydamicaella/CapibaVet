package br.com.start.meupet.user.usecase;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.service.ServiceUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserFromTokenUseCase {

    private final ServiceUtils serviceUtils;
    private final UserRepository userRepository;

    public RegisterUserFromTokenUseCase(ServiceUtils serviceUtils, UserRepository userRepository) {
        this.serviceUtils = serviceUtils;
        this.userRepository = userRepository;
    }

    public User execute(Claims parsedToken) {
        String email = parsedToken.getSubject();
        String name = parsedToken.get("name", String.class);
        String socialName = parsedToken.get("socialName", String.class);
        String phoneNumber = parsedToken.get("phoneNumber", String.class);
        String password = parsedToken.get("password", String.class);
        String document = parsedToken.get("document", String.class);
        String documentType = parsedToken.get("documentType", String.class);

        User entity = new User();
        entity.setEmail(new Email(email));
        entity.setName(name);
        if(socialName == null || socialName.isEmpty()) {
            String firstName = name.split("\\s+")[0]; // Divide o nome por espaços e pega a primeira palavra
            entity.setSocialName(firstName);
        } else {
            entity.setSocialName(socialName);
        }
        entity.setPhoneNumber(new PhoneNumber(phoneNumber));
        entity.setPassword(password);
        entity.setPersonalRegistration(new PersonalRegistration(document, DocumentType.valueOf(documentType.trim().toUpperCase())));
        serviceUtils.isUserAlreadyExists(entity);
        return userRepository.save(entity);
    }


}
