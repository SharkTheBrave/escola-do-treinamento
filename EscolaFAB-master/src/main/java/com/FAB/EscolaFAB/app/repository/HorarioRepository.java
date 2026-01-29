package com.FAB.EscolaFAB.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FAB.EscolaFAB.app.model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    // Ordenar horários por início ajuda na exibição visual da grade
    List<Horario> findAllByOrderByInicioAsc();
}