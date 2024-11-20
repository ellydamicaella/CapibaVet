package br.com.start.meupet.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.start.meupet.user.model.User;
import br.com.start.meupet.common.interfaces.Authenticable;
import br.com.start.meupet.user.repository.UserRepository;
import br.com.start.meupet.common.valueobjects.Email;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User userEntity = new User();
        userEntity.setEmail(new Email(email));
        Optional<Authenticable> user = Optional.ofNullable(userRepository.findByEmail(userEntity.getEmail()));
        if (user.isPresent()) {
            return AuthenticableDetailsImpl.build(user.get());
        }
        throw new UsernameNotFoundException("Usuario nao encontrado");
    }

}
