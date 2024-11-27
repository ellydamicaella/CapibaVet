package br.com.start.meupet.agendamento.repositories;

import br.com.start.meupet.agendamento.model.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {
}
