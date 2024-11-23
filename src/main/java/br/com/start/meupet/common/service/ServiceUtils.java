package br.com.start.meupet.common.service;

import br.com.start.meupet.common.usecase.authenticable.CheckIfAuthenticableAlreadyExistsUseCase;
import br.com.start.meupet.partner.model.Partner;
import org.springframework.stereotype.Service;
import br.com.start.meupet.user.model.User;

@Service
public class ServiceUtils {

    private final CheckIfAuthenticableAlreadyExistsUseCase checkIfAuthenticableAlreadyExists;

    public ServiceUtils(CheckIfAuthenticableAlreadyExistsUseCase checkIfAuthenticableAlreadyExists) {
        this.checkIfAuthenticableAlreadyExists = checkIfAuthenticableAlreadyExists;
    }

    public void isUserAlreadyExists(User user) {
        checkIfAuthenticableAlreadyExists.execute(user);
    }

    public void isPartnerAlreadyExists(Partner partner) {
        checkIfAuthenticableAlreadyExists.execute(partner);
    }

}
