package org.example.tp_billetterie.Service;

import org.example.tp_billetterie.Entity.Customer;
import org.example.tp_billetterie.Entity.Event;
import org.example.tp_billetterie.Entity.Ticket;
import org.example.tp_billetterie.Enum.SeatType;
import org.example.tp_billetterie.Exception.NotFoundException;
import org.example.tp_billetterie.Repository.TicketRepository;
import org.example.tp_billetterie.Validator.SeatAvailabilityValidator;
import java.util.List;

public class TicketService extends BaseCrudService<Ticket> {
    private final TicketRepository ticketRepository;
    private final CustomerService customerService;
    private final EventService eventService;

    public TicketService(CustomerService customerService, EventService eventService) {
        super("Ticket");
        this.ticketRepository = new TicketRepository();
        this.customerService = customerService;
        this.eventService = eventService;
    }

    @Override
    public void create(Ticket ticket) {
        ticketRepository.save(ticket);
        logSuccess("created", getEntityIdentifier(ticket));
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getById(int id) throws NotFoundException {
        Ticket ticket = ticketRepository.findById(id);
        if (ticket == null) {
            throwNotFound(id);
        }
        return ticket;
    }

    @Override
    public void update(int id, Ticket updatedTicket) throws NotFoundException {
        Ticket existingTicket = getById(id);
        updateTicketFields(existingTicket, updatedTicket);
        logSuccess("updated", getEntityIdentifier(existingTicket));
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Ticket ticket = getById(id);

        ticket.getCustomer().removeTicket(ticket);
        ticket.getEvent().removeTicket(ticket);

        ticketRepository.deleteById(id);
        logSuccess("deleted", getEntityIdentifier(ticket));
    }

    @Override
    protected String getEntityIdentifier(Ticket ticket) {
        return "ID: " + ticket.getId() + " (Seat " + ticket.getSeatNumber() + ")";
    }

    private void updateTicketFields(Ticket existing, Ticket updated) {
        existing.setSeatNumber(updated.getSeatNumber());
        existing.setSeatType(updated.getSeatType());
    }

    public void createTicket(int customerId, int eventId, int seatNumber, SeatType seatType)
            throws NotFoundException {
        try {
            Customer customer = customerService.getById(customerId);
            Event event = eventService.getById(eventId);

            SeatAvailabilityValidator.validateSeat(event, seatNumber);

            Ticket ticket = new Ticket(seatNumber, customer, event, seatType);

            ticketRepository.save(ticket);
            customer.addTicket(ticket);
            event.addTicket(ticket);

            System.out.println("Ticket reserved successfully: " + ticket);

        } catch (RuntimeException e) {
            System.out.println("Reservation failed: " + e.getMessage());
            throw e;
        }
    }

    public void updateTicket(int id, int seatNumber, SeatType seatType) throws NotFoundException {
        Ticket ticket = getById(id);

        SeatAvailabilityValidator.validateSeat(ticket.getEvent(), seatNumber);

        ticket.setSeatNumber(seatNumber);
        ticket.setSeatType(seatType);
        logSuccess("updated", getEntityIdentifier(ticket));
    }
}