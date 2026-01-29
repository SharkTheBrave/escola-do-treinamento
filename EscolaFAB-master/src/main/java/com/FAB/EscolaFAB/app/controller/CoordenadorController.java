package com.FAB.EscolaFAB.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.FAB.EscolaFAB.app.model.Aluno;
import com.FAB.EscolaFAB.app.model.Cronograma;
import com.FAB.EscolaFAB.app.model.Disciplina;
import com.FAB.EscolaFAB.app.model.Horario;
import com.FAB.EscolaFAB.app.model.Professor;
import com.FAB.EscolaFAB.app.model.Turma;
import com.FAB.EscolaFAB.app.repository.AlunoRepository;
import com.FAB.EscolaFAB.app.repository.CronogramaRepository;
import com.FAB.EscolaFAB.app.repository.DisciplinaRepository;
import com.FAB.EscolaFAB.app.repository.HorarioRepository;
import com.FAB.EscolaFAB.app.repository.ProfessorRepository;
import com.FAB.EscolaFAB.app.repository.TurmaRepository;

@Controller
@RequestMapping("/coordenadoria")
public class CoordenadorController {

    @Autowired private ProfessorRepository profRepo;
    @Autowired private AlunoRepository alunoRepo;
    @Autowired private TurmaRepository turmaRepo;
    @Autowired private DisciplinaRepository discRepo;
    @Autowired private HorarioRepository horaRepo;
    @Autowired private CronogramaRepository cronoRepo;

    @GetMapping
    public String painel(Model model) {
        // Carrega listas para preencher os <select> dos formulários
        model.addAttribute("professores", profRepo.findAll());
        model.addAttribute("turmas", turmaRepo.findAll());
        model.addAttribute("disciplinas", discRepo.findAll());
        model.addAttribute("horarios", horaRepo.findAllByOrderByInicioAsc());
        return "coordenadoria";
    }

    // --- Métodos de Salvamento (CRUD) ---

    @PostMapping("/salvarProfessor")
    public String salvarProfessor(Professor professor) {
        profRepo.save(professor);
        return "redirect:/coordenadoria";
    }

    @PostMapping("/salvarTurma")
    public String salvarTurma(Turma turma) {
        turmaRepo.save(turma);
        return "redirect:/coordenadoria";
    }
    
    @PostMapping("/salvarDisciplina")
    public String salvarDisciplina(Disciplina disciplina) {
        discRepo.save(disciplina);
        return "redirect:/coordenadoria";
    }

    @PostMapping("/salvarHorario")
    public String salvarHorario(Horario horario) {
        horaRepo.save(horario);
        return "redirect:/coordenadoria";
    }

    @PostMapping("/salvarAluno")
    public String salvarAluno(Aluno aluno) {
        alunoRepo.save(aluno);
        return "redirect:/coordenadoria";
    }
    
    @PostMapping("/salvarCronograma")
    public String salvarCronograma(Cronograma cronograma) {
        cronoRepo.save(cronograma);
        return "redirect:/coordenadoria";
    }
}