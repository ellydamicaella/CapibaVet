package br.com.start.meupet.agendamento.repositories;

import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoMarcadoRepository extends JpaRepository<AtendimentoMarcado, Long> {
}
