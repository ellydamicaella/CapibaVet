package br.com.start.meupet.user.service;

import java.util.Optional;

import br.com.start.meupet.common.service.AuthenticableDetailsImpl;
import br.com.start.meupet.common.usecase.authenticable.FindAuthenticableUseCase;
import br.com.start.meupet.partner.model.Partner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.common.interfaces.Authenticable;
import br.com.start.meupet.common.valueobjects.Email;

@Slf4j
@Service
public class AuthenticableDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private FindAuthenticableUseCase findAuthenticableUseCase;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Email userEmail = new Email(email);

        // Primeiro, tenta carregar o usuário
        Optional<Authenticable> authenticable = findAuthenticableUseCase.byEmail(userEmail);

        if(authenticable.isPresent()) {
            if(authenticable.get() instanceof User user) {
                return AuthenticableDetailsImpl.build(user);
            } else if(authenticable.get() instanceof Partner partner) {
                return AuthenticableDetailsImpl.build(partner);
            }
        }
        // Se não encontrar, lança exceção
        throw new UsernameNotFoundException("Usuario nao encontrado");
    }
}
