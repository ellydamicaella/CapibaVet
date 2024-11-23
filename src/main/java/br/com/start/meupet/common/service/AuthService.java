package br.com.start.meupet.common.service;

import br.com.start.meupet.common.usecase.auth.LoginUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.com.start.meupet.common.dto.AccessDTO;
import br.com.start.meupet.common.dto.AuthenticationDTO;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final LoginUseCase loginUseCase;

    public AuthService(
            LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public AccessDTO login(AuthenticationDTO authDTO) {
        return loginUseCase.execute(authDTO);
    }

}
