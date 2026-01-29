package com.FAB.EscolaFAB.app.controller;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.FAB.EscolaFAB.app.model.Horario;
import com.FAB.EscolaFAB.app.model.Turma;
import com.FAB.EscolaFAB.app.repository.AlunoRepository;
import com.FAB.EscolaFAB.app.repository.AvisoRepository;
import com.FAB.EscolaFAB.app.repository.CronogramaRepository;
import com.FAB.EscolaFAB.app.repository.FaltaRepository;
import com.FAB.EscolaFAB.app.repository.HorarioRepository;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired private AlunoRepository alunoRepo;
    @Autowired private CronogramaRepository cronoRepo;
    @Autowired private AvisoRepository avisoRepo;
    @Autowired private HorarioRepository horarioRepo;
    @Autowired private FaltaRepository faltaRepo;

    @GetMapping("/{id}/home")
    public String home(@PathVariable("id") Long idAluno, Model model) {

       var alunoOpt = alunoRepo.findById(idAluno);
        if (alunoOpt.isEmpty()) return "redirect:/?erro=aluno_inexistente";

        Aluno aluno = alunoOpt.get();
        Turma turma = aluno.getTurma();

        List<Aviso> avisos = avisoRepo.findByTurmaIdOrTurmaIsNull(turma.getId());
        List<Horario> horarios = horarioRepo.findAllByOrderByInicioAsc();
        List<Cronograma> aulas = cronoRepo.findByTurmaId(turma.getId());
        List<Falta> faltas = faltaRepo.findByAlunoId(idAluno);

        Map<String, String> mapaGrade = new HashMap<>();
        for (Cronograma c : aulas) {
            String chave = c.getHorario().getId() + "-" + c.getDiaSemana();
            mapaGrade.put(chave, c.getDisciplina().getNome());
        }

        model.addAttribute("aluno", aluno);
        model.addAttribute("avisos", avisos);
        model.addAttribute("horarios", horarios);
        model.addAttribute("diasSemana", List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        model.addAttribute("mapaGrade", mapaGrade);
        model.addAttribute("faltas", faltas);

        return "aluno";
    }

    @PostMapping("/salvarJustificativa")
    public String salvarJustificativa(
            @RequestParam Long idAluno, 
            @RequestParam Long idFalta, 
            @RequestParam String texto) {
        
        Falta falta = faltaRepo.findById(idFalta).orElseThrow();
        falta.setJustificativa(texto);
        faltaRepo.save(falta);
        
        return "redirect:/aluno/" + idAluno + "/home";
    }
}