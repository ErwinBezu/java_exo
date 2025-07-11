package org.example.tp_billetterie.Controller;

import org.example.tp_billetterie.Entity.*;
import org.example.tp_billetterie.Enum.SeatType;
import org.example.tp_billetterie.Exception.NotFoundException;
import org.example.tp_billetterie.Service.*;
import java.util.List;
import java.util.Scanner;

public class TicketController extends BaseController<Ticket> {
    private final TicketService ticketService;
    private final EventService eventService;
    private final CustomerService customerService;

    public TicketController(TicketService ticketService, EventService eventService,
                            CustomerService customerService, Scanner scanner) {
        super(scanner, "Ticket");
        this.ticketService = ticketService;
        this.eventService = eventService;
        this.customerService = customerService;
    }

    @Override
    public void showMenu() {
        showStandardMenu();
    }

    @Override
    protected boolean executeChoice(int choice) throws Exception {
        return handleStandardChoice(choice, 0);
    }

    @Override
    protected void performCreate() {
        printCreateHeader();

        try {
            Customer customer = selectCustomer();
            Event event = selectEvent();
            int seatNumber = promptForInt("Enter seat number");
            SeatType seatType = selectSeatType();

            Ticket ticket = ticketService.createTicketWithValidation(customer.getId(), event.getId(), seatNumber, seatType);

            System.out.println("Ticket reserved successfully: " + ticket);

        } catch (Exception e) {
            System.out.println("Reservation failed: " + e.getMessage());
        }
    }

    @Override
    protected void performReadAll() {
        printReadAllHeader();
        List<Ticket> tickets = ticketService.getAll();
        printEntitiesList(tickets);
    }

    @Override
    protected void performReadById() throws NotFoundException {
        int id = promptForId("display");

        Ticket ticket = ticketService.getById(id);

        printReadByIdHeader();
        System.out.println(ticket);
        System.out.println("Event: " + ticket.getEvent().getName());
        System.out.println("Customer: " + ticket.getCustomer().getFirstName() + " " +
                ticket.getCustomer().getLastName());
    }

    @Override
    protected void performUpdate() throws NotFoundException {
        int id = promptForId("update");

        Ticket existingTicket = ticketService.getById(id);

        System.out.println("Current ticket: " + existingTicket);

        try {
            int seatNumber = promptForInt("New seat number (current: " + existingTicket.getSeatNumber() + ")");
            SeatType seatType = selectSeatType();

            ticketService.updateTicketWithValidation(id, seatNumber, seatType);

            printSuccessMessage("updated");

        } catch (Exception e) {
            System.out.println(" Update failed: " + e.getMessage());
        }
    }

    @Override
    protected void performDelete() throws NotFoundException {
        int id = promptForId("cancel");

        ticketService.delete(id);

        System.out.println("Ticket cancelled successfully!");
    }

    private Customer selectCustomer() throws NotFoundException {
        System.out.println("\nAvailable customers:");

        List<Customer> customers = customerService.getAll();

        for (Customer customer : customers) {
            System.out.println("  " + customer);
        }

        int customerId = promptForInt("Enter customer ID");
        return customerService.getById(customerId);
    }

    private Event selectEvent() throws NotFoundException {
        System.out.println("\nAvailable events:");

        List<Event> events = eventService.getAll();

        for (Event event : events) {
            System.out.println("  " + event);
        }

        int eventId = promptForInt("Enter event ID");
        return eventService.getById(eventId);
    }

    private SeatType selectSeatType() {
        System.out.println("Select seat type:");
        System.out.println("1. STANDARD");
        System.out.println("2. GOLD");
        System.out.println("3. VIP");
        int choice = promptForInt("Choose seat type");

        switch (choice) {
            case 1: return SeatType.STANDARD;
            case 2: return SeatType.GOLD;
            case 3: return SeatType.VIP;
            default: return SeatType.STANDARD;
        }
    }
}