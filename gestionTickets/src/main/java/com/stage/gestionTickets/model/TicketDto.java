 package com.stage.gestionTickets.model;

import jakarta.validation.constraints.Size;

public class TicketDto {

    private String branche;

    @Size(min = 1, message = "The description should be at least 1 character")
    @Size(max = 2000, message = "The description should not exceed 2000 characters")
    private String textarea;

	private int id_ticket;

    public String getBranche() {
        return branche;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }

    public String getTextarea() {
        return textarea;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }

	public void setId_ticket(int id_ticket) {
		 this.id_ticket = id_ticket;		
	}
}