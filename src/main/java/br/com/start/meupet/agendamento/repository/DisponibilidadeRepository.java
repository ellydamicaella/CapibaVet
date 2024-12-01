package br.com.start.meupet.agendamento.repository;

import br.com.start.meupet.agendamento.model.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    @Query("SELECT d.dayOfWeek FROM Disponibilidade d " +
            "WHERE d.partner.id = :partnerId " +
            "AND d.dayOfWeek IN :daysOfWeek " +
            "AND ((:startTime BETWEEN d.startTime AND d.endTime) " +
            "OR (:endTime BETWEEN d.startTime AND d.endTime) " +
            "OR (d.startTime BETWEEN :startTime AND :endTime))")
    List<DayOfWeek> findConflictingDays(
            @Param("partnerId") UUID partnerId,
            @Param("daysOfWeek") List<DayOfWeek> daysOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
