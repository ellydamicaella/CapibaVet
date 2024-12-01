package br.com.start.meupet.agendamento.repository;

import br.com.start.meupet.agendamento.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
