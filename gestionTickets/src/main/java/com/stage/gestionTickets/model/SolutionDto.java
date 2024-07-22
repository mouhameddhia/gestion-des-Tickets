package com.stage.gestionTickets.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SolutionDto {
	
	
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public int getId_ticket() {
		return id_ticket;
	}
	public void setId_ticket(int id_ticket) {
		this.id_ticket = id_ticket;
	}
	@NotEmpty(message = "reponse is required")
	private String reponse;
   @NotNull(message = "id should not be null")
   private int id_ticket;
}
