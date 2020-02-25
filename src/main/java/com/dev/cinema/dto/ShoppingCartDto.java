package com.dev.cinema.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ShoppingCartDto {
    @NotNull(message = "User id couldn't be null!")
    private Long userId;
    private List<TicketDto> tickets;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<TicketDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }
}
