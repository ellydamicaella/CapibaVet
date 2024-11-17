package br.com.start.meupet.service;

import br.com.start.meupet.domain.entities.Partner;
import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.entities.VerifyAuthenticableEntity;
import br.com.start.meupet.domain.repository.PartnerRepository;
import br.com.start.meupet.domain.repository.UserRepository;
import br.com.start.meupet.dto.PartnerRequestDTO;
import br.com.start.meupet.dto.PartnerResponseDTO;
import br.com.start.meupet.dto.UserRequestDTO;
import br.com.start.meupet.dto.UserResponseDTO;
import br.com.start.meupet.exceptions.UserNotFoundException;
import br.com.start.meupet.mappers.UserMapper;
import br.com.start.meupet.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PartnerService {

    private static final Logger log = LoggerFactory.getLogger(PartnerService.class);
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ServiceUtils serviceUtils;

    public PartnerService(
            PartnerRepository partnerRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService, ServiceUtils serviceUtils) {
        this.partnerRepository = partnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.serviceUtils = serviceUtils;
    }
    public List<PartnerResponseDTO> listAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Partner> partners = partnerRepository.findAll(pageable).getContent();
        log.info("Parceiros listados :{}", partners.stream().map(Partner::getId).collect(Collectors.toList()));
        return partners.stream().map(PartnerResponseDTO::new).toList();
    }

    public PartnerResponseDTO getUserById(UUID id) {
        Partner userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.userToResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO insert(UserRequestDTO userRequest) {
        User userEntity = UserMapper.userRequestToUser(userRequest);

        serviceUtils.isUserAlreadyExists(userEntity);

        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // userRepository.save(userEntity);

        log.info("Usuario criado :{}", userEntity);

        log.info("getEmail :{}", userEntity.getEmail().toString());
        log.info("getName :{}", userEntity.getName());
        log.info("getPhoneNumber :{}", userEntity.getPhoneNumber().toString());
        log.info("getPassword :{}", userEntity.getPassword());

        String token = new JwtUtils().generateTokenFromUserVerifyDetailsImpl(
                userEntity.getEmail().toString(),
                userEntity.getName(),
                userEntity.getPhoneNumber().toString(),
                userEntity.getPassword());

        log.info("token :{}", token);

        VerifyAuthenticableEntity verifyEntity = new VerifyAuthenticableEntity(token);

        log.info("verifyEntity :{}", verifyEntity);

        emailService.sendEmailTemplate(userEntity.getEmail().toString(), "Novo usuário cadastrado",
                userEntity.getName(), verifyEntity.getToken());

        return UserMapper.userToResponseDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO update(UUID id, UserRequestDTO newUser) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!newUser.getEmail().equals(userEntity.getEmail().toString())) {
            serviceUtils.isUserAlreadyExists(userEntity);
        }
        if (!newUser.getPhoneNumber().equals(userEntity.getPhoneNumber().toString())) {
            serviceUtils.isUserAlreadyExists(userEntity);
        }

        User updatedUser = UserMapper.userBeforeToNewUser(userEntity, UserMapper.userRequestToUser(newUser));
        userRepository.save(updatedUser);

        log.info("Usuario atualizado :{}", updatedUser);
        return UserMapper.userToResponseDTO(updatedUser);
    }

    @Transactional
    public void delete(UUID id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(userEntity);
        log.info("Usuario deletado :{}", userEntity);
    }
}
