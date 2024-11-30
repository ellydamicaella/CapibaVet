package br.com.start.meupet.agendamento.repositories;

import br.com.start.meupet.agendamento.model.ServicoPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {
}
