package br.com.start.meupet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.repository.UserRepository;
import br.com.start.meupet.exceptions.ProblemDetailsException;

@Service
public class ServiceUtils {
    @Autowired
    UserRepository userRepository;

    public void isUserAlreadyExists(User user) {
        Optional<User> alreadyHavePhoneNumber = Optional
                .ofNullable(userRepository.findByPhoneNumber(user.getPhoneNumber()));

        if (alreadyHavePhoneNumber.isPresent()) {
            throw new ProblemDetailsException("Usuario já existe", "Já existe um usuário com este telefone",
                    HttpStatus.CONFLICT);
        }

        Optional<User> alreadyHaveEmail = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        if (alreadyHaveEmail.isPresent()) {
            throw new ProblemDetailsException("Usuario já existe", "Já existe um usuário com este e-mail",
                    HttpStatus.CONFLICT);
        }
    }

}
