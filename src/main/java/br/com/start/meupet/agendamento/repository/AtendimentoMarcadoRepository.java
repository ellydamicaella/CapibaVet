package br.com.start.meupet.agendamento.repository;

import br.com.start.meupet.agendamento.model.AtendimentoMarcado;
import br.com.start.meupet.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AtendimentoMarcadoRepository extends JpaRepository<AtendimentoMarcado, Long> {

    @Query("SELECT COUNT(a) > 0 FROM AtendimentoMarcado a " +
            "WHERE a.partner.id = :partnerId " +
            "AND a.appointmentDate = :date " +
            "AND ((a.startTime < :endTime AND a.endTime > :startTime))")
    boolean existsConflict(@Param("partnerId") UUID partnerId,
                           @Param("date") LocalDate date,
                           @Param("startTime") LocalTime startTime,
                           @Param("endTime") LocalTime endTime);

    List<AtendimentoMarcado> findByUser(User user);

}
