package org.example.tp_billetterie.Controller;

import org.example.tp_billetterie.Entity.*;
import org.example.tp_billetterie.Exception.NotFoundException;
import org.example.tp_billetterie.Service.EventService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class EventController extends BaseController<Event> {
    private final EventService eventService;

    public EventController(EventService eventService, Scanner scanner) {
        super(scanner, "Event");
        this.eventService = eventService;
    }

    @Override
    public void showMenu() {
        showStandardMenu("View Event Tickets");
    }

    @Override
    protected boolean executeChoice(int choice) throws Exception {
        return handleStandardChoice(choice, 1);
    }

    @Override
    protected boolean handleExtraChoice(int choice) {
        switch (choice) {
            case 6:
                try {
                    viewEventTickets();
                    return true;
                } catch (NotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                    return true;
                }
            default:
                System.out.println("Invalid choice.");
                return true;
        }
    }

    @Override
    protected void performCreate() {
        printCreateHeader();

        String name = promptForString("Event name");
        Venue venue = createVenue();
        LocalDate date = promptForDate("Date (YYYY-MM-DD)");
        LocalTime time = promptForTime("Time (HH:MM)");
        int numberOfSeats = promptForInt("Number of seats");

        Event event = new Event(name, venue, date, time, numberOfSeats);

        eventService.create(event);

        printSuccessMessage("created");
    }

    @Override
    protected void performReadAll() {
        printReadAllHeader();
        List<Event> events = eventService.getAll();
        printEntitiesList(events);
    }

    @Override
    protected void performReadById() throws NotFoundException {
        int id = promptForId("display");

        Event event = eventService.getById(id);

        printReadByIdHeader();
        System.out.println(event);
        System.out.println("Available seats: " + event.getAvailableSeats() + "/" + event.getNumberOfSeats());
    }

    @Override
    protected void performUpdate() throws NotFoundException {
        int id = promptForId("update");

        Event existingEvent = eventService.getById(id);

        System.out.println("Current event: " + existingEvent);

        String name = promptForStringWithDefault("event name", existingEvent.getName());
        Venue newVenue = updateVenueIfNeeded(existingEvent.getVenue());
        LocalDate date = promptForDateWithDefault("date (YYYY-MM-DD)", existingEvent.getDate());
        LocalTime time = promptForTimeWithDefault("time (HH:MM)", existingEvent.getTime());
        int numberOfSeats = promptForSeatsWithValidation(existingEvent);

        Event updatedEvent = new Event(name, newVenue, date, time, numberOfSeats);

        eventService.update(id, updatedEvent);

        printSuccessMessage("updated");
    }

    @Override
    protected void performDelete() throws NotFoundException {
        int id = promptForId("delete");

        Event event = eventService.getById(id);

        if (!validateDeletionWithTickets(event.getTicketList().size())) {
            return;
        }

        eventService.delete(id);

        printSuccessMessage("deleted");
    }

    private Venue updateVenueIfNeeded(Venue currentVenue) {
        if (promptForConfirmation("Do you want to update the venue?")) {
            return createVenue();
        }
        return currentVenue;
    }

    private int promptForSeatsWithValidation(Event existingEvent) {
        int currentSeats = existingEvent.getNumberOfSeats();
        int soldTickets = existingEvent.getTicketList().size();

        System.out.println("Current seats: " + currentSeats + " | Sold tickets: " + soldTickets);

        while (true) {
            System.out.print("New number of seats (minimum: " + soldTickets + ", press Enter to keep " + currentSeats + "): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return currentSeats;
            }

            try {
                int newSeats = Integer.parseInt(input);
                if (newSeats < soldTickets) {
                    System.out.println("Cannot set seats to " + newSeats + ". You have " + soldTickets + " tickets already sold!");
                    continue;
                }

                if (newSeats <= 0) {
                    System.out.println("Number of seats must be positive!");
                    continue;
                }

                if (newSeats < currentSeats) {
                    if (!promptForConfirmation("You are reducing seats from " + currentSeats + " to " + newSeats + ". Are you sure?")) {
                        continue;
                    }
                }
                return newSeats;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private void viewEventTickets() throws NotFoundException {
        int id = promptForId("view tickets");

        Event event = eventService.getById(id);
        List<Ticket> tickets = eventService.getEventTickets(id);

        System.out.println("\n=== TICKETS FOR EVENT: " + event.getName() + " ===");
        if (tickets.isEmpty()) {
            System.out.println("No tickets found for this event.");
        } else {
            for (Ticket ticket : tickets) {
                System.out.println("  " + ticket);
            }
        }
    }
}