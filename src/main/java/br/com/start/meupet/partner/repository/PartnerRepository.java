package br.com.start.meupet.domain.repository;

import br.com.start.meupet.domain.entities.Partner;
import br.com.start.meupet.domain.valueobjects.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {

    Partner findByEmail(Email email);
}
