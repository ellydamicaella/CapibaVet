package br.com.start.meupet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.start.meupet.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
