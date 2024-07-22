package com.stage.gestionTickets.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.stage.gestionTickets.model.Solution;
import com.stage.gestionTickets.model.Ticket;
import com.stage.gestionTickets.model.TicketDto;
import com.stage.gestionTickets.repository.SolutionRepository;
import com.stage.gestionTickets.repository.TicketRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Tickets")
@CrossOrigin(origins = "http://localhost:3000")
public class TicketController {

    @Autowired
    private TicketRepository repo;
    
    @Autowired
    private SolutionRepository solutionRepo;

    @GetMapping
    public String showTicketList(Model model) {
        List<Ticket> tickets = repo.findAll();
        model.addAttribute("tickets", tickets);
        return "Tickets/index";
    }

    @GetMapping("/CreateTicket")
    public String showAddTicket(Model model) {
        TicketDto ticketDto = new TicketDto();
        model.addAttribute("ticketDto", ticketDto);
        return "Tickets/CreateTicket";
    }

    @PostMapping("/CreateTicket")
    public String createTicket(
        @Valid @ModelAttribute("ticketDto") TicketDto ticketDto,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            return "Tickets/CreateTicket";
        }

        Ticket ticket = new Ticket();
        ticket.setTextarea(ticketDto.getTextarea());
        ticket.setBranche(ticketDto.getBranche());
        repo.save(ticket);

        return "redirect:/Tickets";
    }

    @PostMapping("/CreateTicketJson")
    public @ResponseBody String createTicketJson(@RequestBody TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setTextarea(ticketDto.getTextarea());
        ticket.setBranche(ticketDto.getBranche());
        repo.save(ticket);

        return "New ticket added";
    }

    @GetMapping("/getAllTickets")
    public @ResponseBody List<Ticket> getAllTickets() {
        return repo.findAll();
    }

    @GetMapping("/EditTicket/{id_ticket}")
    public String showEditTicket(@PathVariable("id_ticket") int id_ticket, Model model) {
        Ticket ticket = repo.findById(id_ticket)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id_ticket));
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTextarea(ticket.getTextarea());
        ticketDto.setBranche(ticket.getBranche());
        model.addAttribute("ticketDto", ticketDto);
        model.addAttribute("ticketId", id_ticket);
        return "Tickets/EditTicket";
    }

    
    @GetMapping("/Details/{id_ticket}")
    public @ResponseBody TicketDto getTicketDetails(@PathVariable("id_ticket") int id_ticket) {
        Ticket ticket = repo.findById(id_ticket)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id_ticket));
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTextarea(ticket.getTextarea());
        ticketDto.setBranche(ticket.getBranche());
        ticketDto.setId_ticket(ticket.getId_ticket()); // Ensure this method is available in TicketDto
        return ticketDto;
    }

    
    @PostMapping("/EditTicket/{id_ticket}")
    public String updateTicket(
        @PathVariable("id_ticket") int id_ticket,
        @Valid @ModelAttribute("ticketDto") TicketDto ticketDto,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("ticketId", id_ticket);
            return "Tickets/EditTicket";
        }

        Ticket ticket = repo.findById(id_ticket)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id_ticket));
        ticket.setTextarea(ticketDto.getTextarea());
        ticket.setBranche(ticketDto.getBranche());
        repo.save(ticket);

        return "redirect:/Tickets";
    }
    
    
    @GetMapping("/DeleteTicket/{id_ticket}")
    public String showDeleteTicket(@PathVariable("id_ticket") int id_ticket, Model model) {
        model.addAttribute("ticketId", id_ticket);
        return "Tickets/DeleteTicket";
    }

    @PostMapping("/DeleteTicket/{id_ticket}")
    public String deleteTicket(@PathVariable("id_ticket") int id_ticket) {
        Ticket ticket = repo.findById(id_ticket)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id_ticket));
        repo.delete(ticket);
        return "redirect:/Tickets";
    }
    
    
    
    @PostMapping("/Tickets/AddSolution")
    public String addSolution(@RequestParam int id_ticket, @RequestParam String reponse, Model model) {
        Optional<Ticket> optionalTicket = repo.findById(id_ticket);
        if (optionalTicket.isEmpty()) {
            throw new IllegalArgumentException("Invalid ticket Id: " + id_ticket);
        }

        Ticket ticket = optionalTicket.get();
        Solution solution = new Solution();
        solution.setTicket(ticket);
        solution.setReponse(reponse);

        solutionRepo.save(solution);

        return "redirect:/Tickets";
    }






    @GetMapping("/AddSolutionForm/{id_ticket}")
    public String showAddSolutionForm(@PathVariable("id_ticket") int id_ticket, Model model) {
        Ticket ticket = repo.findById(id_ticket)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id_ticket));
        model.addAttribute("ticket", ticket);
        return "Tickets/AddSolutionForm";
    }

    
}


    
    
    //*************************************************************************
    
    
    
    
    
    
    /* New method to create a Solution
    @PostMapping("/CreateSolution")
    public @ResponseBody String createSolution(@RequestBody Solution solution) {
        solutionRepo.save(solution);
        return "New solution added";
    }

    // New method to get all Solutions
    @GetMapping("/getAllSolutions")
    public @ResponseBody List<Solution> getAllSolutions() {
        return solutionRepo.findAll();
    }*/
    
    
    
    
    

