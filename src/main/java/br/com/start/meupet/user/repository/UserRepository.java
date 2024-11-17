package br.com.start.meupet.domain.repository;


import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(Email email);

    User findByPhoneNumber(PhoneNumber PhoneNumber);
}
