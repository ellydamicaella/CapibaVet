package br.com.start.meupet.user.repository;


import br.com.start.meupet.common.valueobjects.PersonalRegistration;
import br.com.start.meupet.user.model.User;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.common.valueobjects.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(Email email);

    User findByPhoneNumber(PhoneNumber PhoneNumber);
}
