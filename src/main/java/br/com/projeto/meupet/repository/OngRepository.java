package br.com.projeto.meupet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.meupet.entities.Ong;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {

}
