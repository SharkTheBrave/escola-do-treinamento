package com.FAB.EscolaFAB.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FAB.EscolaFAB.app.model.Aluno;
import com.FAB.EscolaFAB.app.model.Aviso;
import com.FAB.EscolaFAB.app.model.Cronograma;
import com.FAB.EscolaFAB.app.model.Falta;
import com.FAB.EscolaFAB.app.model.Professor;
import com.FAB.EscolaFAB.app.model.Turma;
import com.FAB.EscolaFAB.app.repository.AlunoRepository;
import com.FAB.EscolaFAB.app.repository.AvisoRepository;
import com.FAB.EscolaFAB.app.repository.CronogramaRepository;
import com.FAB.EscolaFAB.app.repository.DisciplinaRepository;
import com.FAB.EscolaFAB.app.repository.FaltaRepository;
import com.FAB.EscolaFAB.app.repository.ProfessorRepository;
import com.FAB.EscolaFAB.app.repository.TurmaRepository;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired private ProfessorRepository profRepo;
    @Autowired private TurmaRepository turmaRepo;
    @Autowired private AvisoRepository avisoRepo;
    @Autowired private AlunoRepository alunoRepo;
    @Autowired private DisciplinaRepository discRepo;
    @Autowired private FaltaRepository faltaRepo;
    @Autowired private CronogramaRepository cronoRepo;

   @GetMapping("/{id}/home")
    public String home(@PathVariable("id") Long idProf, Model model) {
        var profOpt = profRepo.findById(idProf);
        if (profOpt.isEmpty()) return "redirect:/?erro=prof_nao_encontrado";

        Professor professor = profOpt.get();
        
        List<Cronograma> aulas = cronoRepo.findByProfessorId(idProf);
        List<Turma> minhasTurmas = aulas.stream()
                .map(Cronograma::getTurma)
                .distinct()
                .collect(Collectors.toList());
        List<Falta> pendencias = faltaRepo.findByJustificativaIsNotNullAndIsJustificadaFalse();

        model.addAttribute("professor", professor);
        model.addAttribute("turmas", minhasTurmas);
        model.addAttribute("disciplinas", discRepo.findAll());
        model.addAttribute("pendencias", pendencias);

        return "professor";
    }

    // --- Passo 1 da Falta: Selecionar a Turma para ver os alunos ---
    @GetMapping("/{idProf}/chamada/{idTurma}")
    public String chamada(@PathVariable Long idProf, @PathVariable Long idTurma, Model model) {
        
        model.addAttribute("professorId", idProf);
        
        model.addAttribute("turmaAtual", turmaRepo.findById(idTurma).get());
        model.addAttribute("alunos", alunoRepo.findByTurmaId(idTurma));
        model.addAttribute("disciplinas", discRepo.findAll());
        model.addAttribute("dataHoje", java.time.LocalDate.now());
        
        return "professorChamada";
    }

    @PostMapping("/salvarFalta")
    public String salvarFalta(Falta falta, @RequestParam Long idProfessor) {
        Long idAluno = falta.getAluno().getId();
        Aluno alunoReal = alunoRepo.findById(idAluno).orElseThrow();

        falta.setAluno(alunoReal);


        faltaRepo.save(falta);
        Long idTurma = alunoReal.getTurma().getId();
        
        return "redirect:/professor/" + idProfessor + "/chamada/" + idTurma;
    }

    @PostMapping("/salvarAviso")
    public String salvarAviso(Aviso aviso, @RequestParam Long idProfessor) {
        
        // Vincula o aviso ao professor usando o ID que veio do form
        Professor p = new Professor();
        p.setId(idProfessor);
        aviso.setProfessor(p);
        
        avisoRepo.save(aviso);
        
        // Redireciona de volta para a URL com ID
        return "redirect:/professor/" + idProfessor + "/home";
    }

    @PostMapping("/abonarFalta")
    public String abonarFalta(@RequestParam Long idFalta, @RequestParam Long idProfessor) {
        
        Falta falta = faltaRepo.findById(idFalta).orElseThrow();

        falta.setIsJustificada(true);

        faltaRepo.save(falta);

        return "redirect:/professor/" + idProfessor + "/home";
    }
}