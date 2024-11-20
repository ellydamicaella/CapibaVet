package br.com.start.meupet.user.usecase;

import br.com.start.meupet.user.dto.UserResponseDTO;
import br.com.start.meupet.user.mapper.UserMapper;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ListUsersUseCase {

    private final UserRepository userRepository;

    public ListUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> execute(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<User> users = userRepository.findAll(pageable).getContent();
        log.info("Usuario listados :{}", users.stream().map(User::getId).collect(Collectors.toList()));

        return users.stream().map(UserMapper::userToResponseDTO).toList();
    }
}
