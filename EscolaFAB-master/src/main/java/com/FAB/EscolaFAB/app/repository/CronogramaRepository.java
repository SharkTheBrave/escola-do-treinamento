package com.FAB.EscolaFAB.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FAB.EscolaFAB.app.model.Cronograma;

@Repository
public interface CronogramaRepository extends JpaRepository<Cronograma, Long> {
    // Buscar grade completa de uma turma
    List<Cronograma> findByTurmaId(Long idTurma);
    
    // Buscar agenda de um professor
    List<Cronograma> findByProfessorId(Long idProfessor);
}