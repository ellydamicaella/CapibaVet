package br.com.start.meupet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.start.meupet.domain.entities.Ong;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {

}
