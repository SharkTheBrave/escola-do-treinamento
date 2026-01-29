package com.FAB.EscolaFAB.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FAB.EscolaFAB.app.model.Falta;

@Repository
public interface FaltaRepository extends JpaRepository<Falta, Long> {
    // Ver histórico de faltas de um aluno
    List<Falta> findByAlunoId(Long idAluno);
    // Ver as faltas que ainda estão para ser justificadas
    List<Falta> findByJustificativaIsNotNullAndIsJustificadaFalse();
}