package br.com.start.meupet.auth.usecase.authenticable;

import br.com.start.meupet.auth.interfaces.AbstractAuthenticableResponseDTO;
import br.com.start.meupet.common.security.jwt.JwtUtils;
import br.com.start.meupet.partner.mapper.PartnerMapper;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.usecase.RegisterPartnerFromTokenUseCase;
import br.com.start.meupet.user.mapper.UserMapper;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.user.usecase.RegisterUserFromTokenUseCase;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProcessUserRegistrationUseCase {

    private final JwtUtils jwtUtils;
    private final RegisterUserFromTokenUseCase registerUserFromTokenUseCase;
    private final RegisterPartnerFromTokenUseCase registerPartnerFromTokenUseCase;

    public ProcessUserRegistrationUseCase(JwtUtils jwtUtils, RegisterUserFromTokenUseCase registerUserFromTokenUseCase, RegisterPartnerFromTokenUseCase registerPartnerFromTokenUseCase) {
        this.jwtUtils = jwtUtils;
        this.registerUserFromTokenUseCase = registerUserFromTokenUseCase;
        this.registerPartnerFromTokenUseCase = registerPartnerFromTokenUseCase;
    }

    @Transactional
    public AbstractAuthenticableResponseDTO execute(String token) {
        Claims parsedToken = jwtUtils.getParsedToken(token);
        String typeUser = parsedToken.get("typeUser", String.class);

        if (typeUser.equals("PARTNER")) {
            Partner entity = registerPartnerFromTokenUseCase.execute(parsedToken);
            return PartnerMapper.partnerToResponseDTO(entity);
        } else {
            User entity = registerUserFromTokenUseCase.execute(parsedToken);
            return UserMapper.userToResponseDTO(entity);
        }
    }
}
