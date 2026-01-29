package com.FAB.EscolaFAB.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FAB.EscolaFAB.app.repository.AlunoRepository;
import com.FAB.EscolaFAB.app.repository.ProfessorRepository;

@Controller
public class WebController {

    @Autowired 
    private AlunoRepository alunoRepo;
    
    @Autowired private 
    ProfessorRepository profRepo;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Lógica simples de Login (Simulação sem Spring Security por enquanto)
    @PostMapping("/login")
    public String login(@RequestParam String tipo,
                        @RequestParam(required = false) Long idLogin,
                        @RequestParam(required = false) String email,
                        @RequestParam(required = false) String senha) {
        
        if ("coordenadoria".equals(tipo)) {
            boolean emailCorreto = "admin".equals(email) || "admin@escola.com".equals(email);
            
            if (emailCorreto && "123".equals(senha)) {
                return "redirect:/coordenadoria";
            }
        }
        
        if (tipo.equals("aluno") && idLogin != null) {
            if (alunoRepo.existsById(idLogin)) {
                return "redirect:/aluno/" + idLogin + "/home"; 
            }
        }
        
        if (tipo.equals("professor") && idLogin != null) {
            if (profRepo.existsById(idLogin)) {
                return "redirect:/professor/" + idLogin + "/home";
            }
        }

        return "redirect:/?erro=true";
    }
}