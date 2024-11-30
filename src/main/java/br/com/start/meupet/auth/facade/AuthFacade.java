package br.com.start.meupet.auth.facade;

import br.com.start.meupet.auth.usecase.auth.LoginUseCase;
import org.springframework.stereotype.Component;
import br.com.start.meupet.auth.dto.AccessDTO;
import br.com.start.meupet.auth.dto.AuthenticationDTO;

@Component
public class AuthFacade {

    private final LoginUseCase loginUseCase;

    public AuthFacade(
            LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public AccessDTO login(AuthenticationDTO authDTO) {
        return loginUseCase.execute(authDTO);
    }

}
