package br.com.start.meupet.common.usecase;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.exceptions.ProblemDetailsException;
import br.com.start.meupet.common.interfaces.Authenticable;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CheckIfAuthenticableAlreadyExists {

    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;

    public CheckIfAuthenticableAlreadyExists(PartnerRepository partnerRepository, UserRepository userRepository) {
        this.partnerRepository = partnerRepository;
        this.userRepository = userRepository;
    }

    public void execute(Authenticable authenticable) {
        // Verifica se já existe um número de telefone registrado
//        Optional<Authenticable> alreadyHavePhoneNumber = findByPhoneNumber(authenticable.getPhoneNumber());

//        if (alreadyHavePhoneNumber.isPresent()) {
//            if (alreadyHavePhoneNumber.get().getId() != authenticable.getId()) {
//                throw new ProblemDetailsException("Já existe um usuário com este telefone",
//                        "Já existe um usuário com este telefone", HttpStatus.CONFLICT);
//            }
//        }

        // Verifica se já existe um e-mail registrado
        Optional<Authenticable> alreadyHaveEmail = findByEmail(authenticable.getEmail());

        if (alreadyHaveEmail.isPresent()) {
            if(alreadyHaveEmail.get().getId() != authenticable.getId()) {
                throw new ProblemDetailsException("Já existe um usuário com este e-mail",
                        "Já existe um usuário com este e-mail", HttpStatus.CONFLICT);
            }
        }

//        Optional<Authenticable> alreadyHaveDocument = findByDocument(authenticable.getPersonalRegistration());
//
//        if (alreadyHaveDocument.isPresent()) {
//            if(alreadyHaveDocument.get().getId() != authenticable.getId()) {
//                throw new ProblemDetailsException("Já existe um usuario com este e-mail",
//                        "Já existe um usuário com este e-mail", HttpStatus.CONFLICT);
//            }
//        }
    }
//
//    // Metodo para encontrar o usuário ou parceiro pelo número de telefone
//    public Optional<Authenticable> findByPhoneNumber(PhoneNumber phoneNumber) {
//        // Verifica se é um usuário ou parceiro e busca no repositório correspondente
//        if (phoneNumber == null) {
//            return Optional.empty();
//        }
//
//        // Procura tanto no UserRepository quanto no PartnerRepository
//        Optional<Authenticable> userOptional = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber));
//        if (userOptional.isPresent()) {
//            return userOptional;
//        }
//
//        return Optional.ofNullable(partnerRepository.findByPhoneNumber(phoneNumber));
//    }


    // Metodo para encontrar o usuário ou parceiro pelo e-mail
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

//    // Metodo para encontrar o usuário ou parceiro pelo CPF ou CNPJ
//    public Optional<Authenticable> findByDocument(PersonalRegistration personalRegistration) {
//        // Verifica se é um usuário ou parceiro e busca no repositório correspondente
//        if (personalRegistration.getDocument() == null) {
//            return Optional.empty();
//        }
//
//        // Procura tanto no UserRepository quanto no PartnerRepository
//        Optional<Authenticable> userOptional = Optional.ofNullable(userRepository.findByDocument(personalRegistration));
//        if (userOptional.isPresent()) {
//            return userOptional;
//        }
//
//        return Optional.ofNullable(partnerRepository.findByDocument(personalRegistration));
//    }
}
