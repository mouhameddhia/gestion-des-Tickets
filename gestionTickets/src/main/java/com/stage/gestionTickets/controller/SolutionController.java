package com.stage.gestionTickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stage.gestionTickets.model.Solution;
import com.stage.gestionTickets.model.SolutionDto;
import com.stage.gestionTickets.model.Ticket;
import com.stage.gestionTickets.repository.SolutionRepository;
import com.stage.gestionTickets.repository.TicketRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Solutions")
public class SolutionController {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping({"", "/"})
    public String showSolutionList(Model model) {
        List<Solution> solutions = solutionRepository.findAll();
        model.addAttribute("solutions", solutions);
        return "Solutions/indexS"; 
    }

    @GetMapping("/CreateSolution")
    public String showCreateSolutionForm(Model model) {
        SolutionDto solutionDto = new SolutionDto();
        model.addAttribute("solutionDto", solutionDto);
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "Solutions/CreateSolution";
    }

    @PostMapping("/CreateSolution")
    public String createSolution(
        @Valid @ModelAttribute("solutionDto") SolutionDto solutionDto,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            List<Ticket> tickets = ticketRepository.findAll();
            model.addAttribute("tickets", tickets);
            return "Solutions/CreateSolution";
        }
        // Convert DTO to entity and save
        Solution solution = new Solution();
        solution.setReponse(solutionDto.getReponse());
        Ticket ticket = ticketRepository.findById(solutionDto.getId_ticket()).orElse(null);
        solution.setTicket(ticket);
        solutionRepository.save(solution);
        return "redirect:/Solutions";
    }
}
