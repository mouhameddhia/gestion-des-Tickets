package com.stage.gestionTickets.model;

import jakarta.persistence.*;

@Entity
@Table(name= "solution")
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_solution;

    @Column(nullable = false, length = 2000)
    private String reponse;

    @OneToOne
    @JoinColumn(name = "id_ticket", nullable = false) // Foreign key column
    private Ticket ticket;

    // Getters and Setters
    public int getId() {
        return id_solution;
    }

    public void setId(int id_solution) {
        this.id_solution = id_solution;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
