package com.FAB.EscolaFAB.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FAB.EscolaFAB.app.model.Aviso;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {
    // Buscar avisos de uma turma ESPECÍFICA ou avisos GERAIS (turma null)
    List<Aviso> findByTurmaIdOrTurmaIsNull(Long idTurma);
}