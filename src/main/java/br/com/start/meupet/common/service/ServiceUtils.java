package br.com.start.meupet.common.service;

import java.util.Optional;

import br.com.start.meupet.common.interfaces.Authenticable;
import br.com.start.meupet.common.interfaces.AuthenticableResponseDTO;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;

@Service
public class ServiceUtils {

    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;

    public ServiceUtils(UserRepository userRepository, PartnerRepository partnerRepository) {
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
    }

    public void checkIfAlreadyExists(Authenticable authenticable) {
        // Verifica se já existe um número de telefone registrado
        Optional<Authenticable> alreadyHavePhoneNumber = findByPhoneNumber(authenticable.getPhoneNumber());

        if (alreadyHavePhoneNumber.isPresent()) {
            if (!(alreadyHavePhoneNumber.get().getId() == authenticable.getId())) {
            throw new ProblemDetailsException("Já existe um usuário com este telefone",
                    "Já existe um usuário com este telefone", HttpStatus.CONFLICT);
            }
        }

        // Verifica se já existe um e-mail registrado
        Optional<Authenticable> alreadyHaveEmail = findByEmail(authenticable.getEmail());

        if (alreadyHaveEmail.isPresent() && alreadyHaveEmail.get().getId() != authenticable.getId()) {
            if(!(alreadyHaveEmail.get().getId() == authenticable.getId())) {
            throw new ProblemDetailsException("Já existe um usuário com este e-mail",
                    "Já existe um usuário com este e-mail", HttpStatus.CONFLICT);
            }
        }
    }

    // Método para encontrar o usuário ou parceiro pelo número de telefone
    public Optional<Authenticable> findByPhoneNumber(PhoneNumber phoneNumber) {
        // Verifica se é um usuário ou parceiro e busca no repositório correspondente
        if (phoneNumber == null) {
            return Optional.empty();
        }

        // Procura tanto no UserRepository quanto no PartnerRepository
        Optional<Authenticable> userOptional = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber));
        if (userOptional.isPresent()) {
            return userOptional;
        }

        return Optional.ofNullable(partnerRepository.findByPhoneNumber(phoneNumber));
    }


    // Método para encontrar o usuário ou parceiro pelo e-mail
    public Optional<Authenticable> findByEmail(Email email) {
        // Verifica se é um usuário ou parceiro e busca no repositório correspondente
        if (email == null) {
            return Optional.empty();
        }

        // Procura tanto no UserRepository quanto no PartnerRepository
        Optional<Authenticable> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            return userOptional;
        }

        return Optional.ofNullable(partnerRepository.findByEmail(email));
    }

    public void isUserAlreadyExists(User user) {
        checkIfAlreadyExists(user);
    }

    public void isPartnerAlreadyExists(Partner partner) {
        checkIfAlreadyExists(partner);
    }

}
