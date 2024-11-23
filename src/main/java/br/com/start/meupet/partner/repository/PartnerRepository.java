package br.com.start.meupet.partner.repository;

import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.user.model.User;
import jakarta.mail.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, UUID> {

    Partner findByEmail(Email email);

    Partner findByPhoneNumber(PhoneNumber PhoneNumber);

//    Partner findByDocument(PersonalRegistration personalRegistration);
}
