package com.FAB.EscolaFAB.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FAB.EscolaFAB.app.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Buscar todos os alunos de uma turma específica
    List<Aluno> findByTurmaId(Long idTurma);
}