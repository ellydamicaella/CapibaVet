package br.com.start.meupet.partner.usecase;

import br.com.start.meupet.common.enums.DocumentType;
import br.com.start.meupet.common.services.ServiceUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.partner.repository.PartnerRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterPartnerFromTokenUseCase {

    private final ServiceUtils serviceUtils;
    private final PartnerRepository partnerRepository;

    public RegisterPartnerFromTokenUseCase(ServiceUtils serviceUtils, PartnerRepository partnerRepository) {
        this.serviceUtils = serviceUtils;
        this.partnerRepository = partnerRepository;
    }

    public Partner execute(Claims parsedToken) {
        String email = parsedToken.getSubject();
        String name = parsedToken.get("name", String.class);
        String phoneNumber = parsedToken.get("phoneNumber", String.class);
        String password = parsedToken.get("password", String.class);
        String document = parsedToken.get("document", String.class);
        String documentType = parsedToken.get("documentType", String.class);

        Partner entity = new Partner();
        entity.setEmail(new Email(email));
        entity.setName(name);
        entity.setPhoneNumber(new PhoneNumber(phoneNumber));
        entity.setPassword(password);
        entity.setPersonalRegistration(new PersonalRegistration(document, DocumentType.valueOf(documentType.trim().toUpperCase())));
        serviceUtils.isPartnerAlreadyExists(entity);
        return partnerRepository.save(entity);
    }
}
