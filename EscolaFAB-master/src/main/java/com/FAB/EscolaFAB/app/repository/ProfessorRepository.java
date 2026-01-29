package com.FAB.EscolaFAB.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FAB.EscolaFAB.app.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}