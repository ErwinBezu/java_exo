package org.example.tp_billetterie.Repository;

import org.example.tp_billetterie.Entity.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements Repository<Ticket> {
    private final List<Ticket> tickets;

    public TicketRepository() {
        this.tickets = new ArrayList<>();
    }

    @Override
    public void save(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(tickets);
    }

    @Override
    public Ticket findById(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId() == id) {
                tickets.remove(i);
                break;
            }
        }
    }
}