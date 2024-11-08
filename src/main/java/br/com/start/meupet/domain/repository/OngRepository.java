package br.com.start.meupet.domain.repository;

import br.com.start.meupet.domain.entities.Ong;
import br.com.start.meupet.domain.valueobjects.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {

    Ong findByEmail(Email email);
}
