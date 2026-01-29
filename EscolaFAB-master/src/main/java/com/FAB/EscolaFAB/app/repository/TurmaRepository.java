package com.FAB.EscolaFAB.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FAB.EscolaFAB.app.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
}